package io.esb.jbot.util;


import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the White string similarity metric as described here:
 * http://www.catalysoft.com/articles/strikeamatch.html
 */
public class WhiteSimilarity {

    public static double compareStrings(String str1, String str2) {

        List<String> pairs1 = wordLetterPairs(str1.toUpperCase());
        List<String> pairs2 = wordLetterPairs(str2.toUpperCase());
        int intersection = 0;
        int union = pairs1.size() + pairs2.size();

        // Handle the case where the strings are single letters
        if (union == 0) {
            return str1.equalsIgnoreCase(str2) ? 1.0 : 0.0;
        }

        for (String pair1: pairs1) {
            for (int j = 0; j < pairs2.size(); j++) {
                Object pair2 = pairs2.get(j);

                if (pair1.equals(pair2)) {
                    intersection++;
                    pairs2.remove(j);
                    break;
                }
            }
        }

        return (2.0 * intersection) / union;
    }


    private static List<String> wordLetterPairs(String str) {

        List<String> allPairs = new ArrayList<>();

        // Tokenize the string and put the tokens/words into an array
        String[] words = str.split("\\s");

        // For each word
        for (String word : words) {
            // Find the pairs of characters
            allPairs.addAll(letterPairs(word));
        }

        return allPairs;

    }

    /**
     * @return an array of adjacent letter pairs contained in the input string
     */
    private static List<String> letterPairs(String str) {

        int numPairs = str.length() - 1;
        List<String> pairs = new ArrayList<>(numPairs);

        for (int i = 0; i < numPairs; i++) {
            pairs.add(str.substring(i, i+2));
        }

        return pairs;
    }
}
