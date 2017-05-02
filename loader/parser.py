#!/usr/bin/env python -OO
# -*- coding: utf-8 -*-

from __future__ import with_statement, print_function
from bs4 import BeautifulSoup
from glob import glob

import argparse
import os
import re
import psycopg2
import uuid
import sys


def main(args):
    """Loop thru all the games and parse them."""
    if not os.path.isdir(args.dir):
        print("The specified folder is not a directory.")
        sys.exit(1)
    file_count = len(os.listdir(args.dir))
    if args.num_of_files:
        file_count = args.num_of_files
    print("Parsing", file_count, "files")
    sql = None
    conn = None
    if not args.stdout:
        conn = psycopg2.connect(dbname=args.database, host=args.host,
                                port=args.port, user=args.username, password=args.password)
        sql = conn.cursor()

    for _, file_name in enumerate(glob(os.path.join(args.dir, "*.html"))):
        with open(os.path.abspath(file_name)) as f:
            parse_game(f, sql, str(uuid.uuid4()))
    if not args.stdout:
        conn.commit()
    print("All done")


def parse_game(f, sql, gid):
    """Parses an entire Jeopardy! game and extract individual clues."""
    # Don't load if this game is already in the db
    filename = re.search(r'.*/(\d*)\.html', f.name).group(1)
    if already_loaded(sql, filename):
        return

    bsoup = BeautifulSoup(f, "lxml")
    # The title is in the format: `J! Archive - Show #XXXX, aired 2004-09-16`,
    title = bsoup.title.get_text()
    # Meta payload = file name, episode number, air date
    meta = [filename, re.search(r'.*#(\d*).*', title).group(1), title.split()[-1]]

    if not parse_round(bsoup, sql, 1, gid, meta) or not parse_round(bsoup, sql, 2, gid, meta):
        # One of the rounds does not exist
        pass
    # The final Jeopardy! round
    r = bsoup.find("table", class_="final_round")
    if not r:
        # This game does not have a final clue
        return
    category = [str(uuid.uuid4()), r.find("td", class_="category_name").get_text(), None]
    text = r.find("td", class_="clue_text").get_text()
    answer = BeautifulSoup(r.find("div", onmouseover=True).get("onmouseover"), "lxml")
    answer = answer.find("em").get_text()
    media = [m.get("href") for m in r.find_all("a")]
    insert(sql, [gid, meta, 3, category, 0, text, media, answer])


def parse_round(bsoup, sql, rnd, gid, meta):
    """Parses and inserts the list of clues from a whole round."""
    round_id = "jeopardy_round" if rnd == 1 else "double_jeopardy_round"
    r = bsoup.find(id=round_id)
    # The game may not have all the rounds
    if not r:
        return False
    # The list of categories for this round
    cat_names = [c.get_text() for c in r.find_all("td", class_="category_name")]
    cat_hints = [clean_hint(c.get_text()) for c in r.find_all("td", class_="category_comments")]
    labels = [str(uuid.uuid4()) for x in range(6)]
    categories = zip(labels, cat_names, cat_hints)
    # The x_coord determines which category a clue is in
    # because the categories come before the clues, we will
    # have to match them up with the clues later on.
    x = 0
    for a in r.find_all("td", class_="clue"):
        is_missing = True if not a.get_text().strip() else False
        if not is_missing:
            value = a.find("td", class_=re.compile("clue_value")).get_text()
            if value.find("DD") != -1:
                value = 0
            else:
                value = value.lstrip("D: $").replace(",", "")
            text = a.find("td", class_="clue_text").get_text()
            answer = BeautifulSoup(a.find("div", onmouseover=True).get("onmouseover"), "lxml")
            answer = answer.find("em", class_="correct_response").get_text()
            media = [m.get("href") for m in a.find_all("a")][1:]
            insert(sql, [gid, meta, rnd, categories[x], value, text, media, answer])
        # Always update x, even if we skip
        # a clue, as this keeps things in order. there
        # are 6 categories, so once we reach the end,
        # loop back to the beginning category.
        #
        # Using modulus is slower, e.g.:
        #
        # x += 1
        # x %= 6
        #
        x = 0 if x == 5 else x + 1
    return True


def already_loaded(sql, remote_id):
    sql.execute("SELECT 1 from ARCHIVE.GAME WHERE REMOTE_ID = %s", (remote_id, ))
    return sql.fetchone() is not None


def clean_hint(hint):
    return xstr(hint.lstrip("(Alex: ").rstrip(")"))


def insert(sql, clue):
    """Inserts the given clue into the database."""
    # Clue is [game, airdate, round, category, value, clue, media, answer]
    # Note that at this point, clue[4] is False if round is 3
    if "\\\'" in clue[7]:
        clue[7] = clue[7].replace("\\\'", "'")
    if "\\\"" in clue[7]:
        clue[7] = clue[7].replace("\\\"", "\"")

    if not sql:
        print(clue)
        return
    sql.execute(
        "INSERT INTO ARCHIVE.GAME (GAME_ID, REMOTE_ID, SHOW_ID, AIR_DATE) VALUES (%s, %s, %s, %s) " +
        "ON CONFLICT (GAME_ID) DO NOTHING;",
        (clue[0], clue[1][0], clue[1][1], clue[1][2])
    )
    sql.execute(
        "INSERT INTO ARCHIVE.CATEGORY (CATEGORY_ID, NAME, HINT, AIR_DATE, ROUND, GAME_ID) " +
        "VALUES (%s, %s, %s, %s, %s, %s) ON CONFLICT (CATEGORY_ID) DO NOTHING;",
        (clue[3][0], clue[3][1], clue[3][2], clue[1][2], clue[2], clue[0])
    )
    sql.execute(
        "INSERT INTO ARCHIVE.CLUE(QUESTION, VALUE, DAILY_DOUBLE, MEDIA, ANSWER, CATEGORY_ID) " +
        "VALUES (%s, %s, %s, %s, %s, %s);",
        (clue[5], clue[4], clue[4] == 0 and clue[2] != 3, clue[6], [clue[7]], clue[3][0])
    )


def xstr(string):
    """Converts empty strings to None"""
    return string if string != '' else None


if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        description="Parse games from the J! Archive website.", add_help=False,
        usage="%(prog)s [options]")
    parser.add_argument("-f", "--dir", dest="dir", metavar="<folder>",
                        help="the directory containing the game files",
                        default="j-archive")
    parser.add_argument("-n", "--number-of-files", dest="num_of_files",
                        metavar="<number>", help="the number of files to parse",
                        type=int)
    parser.add_argument("-h", "--host", dest="host",
                        metavar="<hostname>",
                        help="the host of the Postgres database",
                        default="localhost")
    parser.add_argument("-r", "--port", dest="port",
                        metavar="<port>",
                        help="the port of the Postgres database",
                        default=5432, type=int)
    parser.add_argument("-d", "--database", dest="database",
                        metavar="<database>",
                        help="the database to use",
                        default="jbot")
    parser.add_argument("-u", "--username", dest="username",
                        metavar="<username>",
                        help="the username used to connect to the database",
                        default="jbot")
    parser.add_argument("-w", "--password", dest="password",
                        metavar="<password>",
                        help="the password used to connect to the database",
                        default="jbot")
    parser.add_argument("--stdout",
                        help="output the clues to stdout and not a database",
                        action="store_true")
    parser.add_argument("--help", action="help",
                        help="show this help message and exit")
    parser.add_argument("--version", action="version", version="2017.05.01")
    main(parser.parse_args())
