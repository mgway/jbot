package io.esb.jbot.dao;


import io.esb.jbot.game.CategoryState;
import io.esb.jbot.game.ClueState;
import io.esb.jbot.game.GameState;
import io.esb.jbot.game.GameState.GameType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GameRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${archive.samplePercent.clue}")
    private int clueSamplePercentage;

    @Value("${archive.samplePercent.category}")
    private int categorySamplePercentage;

    @Value("${archive.samplePercent.game}")
    private int gameSamplePercentage;


    public GameState buildGame(GameType gameType) {
        String query;
        int samplePercentage;

        if (gameType == GameType.CLASSIC) {
            query = "SELECT * FROM ARCHIVE.CLUE INNER JOIN ARCHIVE.CATEGORY USING (CATEGORY_ID) " +
                    "WHERE CATEGORY.CATEGORY_ID IN (SELECT CATEGORY_ID FROM ARCHIVE.CATEGORY TABLESAMPLE SYSTEM (?) LIMIT 6) " +
                    "ORDER BY CATEGORY_ID";
            samplePercentage = categorySamplePercentage;
        } else if (gameType == GameType.REPLAY) {
            query = "SELECT * FROM ARCHIVE.CLUE INNER JOIN ARCHIVE.CATEGORY USING (CATEGORY_ID) " +
                    "WHERE CATEGORY.GAME_ID IN (SELECT GAME_ID FROM ARCHIVE.GAME TABLESAMPLE SYSTEM (?) LIMIT 1) " +
                    "ORDER BY CATEGORY_ID";
            samplePercentage = gameSamplePercentage;
        } else {
            // Random mode
            query = "SELECT * FROM ARCHIVE.CLUE INNER JOIN ARCHIVE.CATEGORY USING (CATEGORY_ID) " +
                    "WHERE CLUE.CLUE_ID IN (SELECT CLUE_ID FROM ARCHIVE.CLUE TABLESAMPLE SYSTEM (?) LIMIT 30) " +
                    "ORDER BY CATEGORY_ID";
            samplePercentage = clueSamplePercentage;

        }

        return jdbcTemplate.query(query, new Object[] {samplePercentage}, resultSet -> {
            GameState game = new GameState();
            game.setId(UUID.randomUUID());
            game.setGameType(gameType);
            game.setStartTime(LocalDateTime.now());
            CategoryState categoryState = null;

            while (resultSet.next()) {
                UUID categoryId = UUID.fromString(resultSet.getString("CATEGORY_ID"));
                if (categoryState == null || !categoryState.getId().equals(categoryId)) {
                    categoryState = mapCategory(resultSet);
                    game.getCategories().add(categoryState);
                }
                game.getClues().add(mapClue(resultSet));
            }

            return game;
        });
    }



    private CategoryState mapCategory(ResultSet resultSet) throws SQLException {
        CategoryState categoryState = new CategoryState();
        categoryState.setId(UUID.fromString(resultSet.getString("CATEGORY_ID")));
        categoryState.setName(resultSet.getString("NAME"));
        categoryState.setHint(resultSet.getString("HINT"));
        categoryState.setAired(resultSet.getDate("AIR_DATE").toLocalDate());

        return categoryState;
    }

    private ClueState mapClue(ResultSet resultSet) throws SQLException {
        ClueState clueState = new ClueState();
        clueState.setId(UUID.fromString(resultSet.getString("CLUE_ID")));
        clueState.setValue(resultSet.getInt("VALUE"));
        clueState.setDailyDouble(resultSet.getBoolean("DAILY_DOUBLE"));
        clueState.setQuestion(resultSet.getString("QUESTION"));
        clueState.setSolutions((String[])resultSet.getArray("ANSWER").getArray());
        Array media = resultSet.getArray("MEDIA");
        if (media != null) {
            clueState.setMedia((String[])media.getArray());
        }

        return clueState;
    }
}
