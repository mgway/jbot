package io.esb.jbot.util;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WhiteSimilarityTests {

    @Test
    public void equalStrings() {
        assertEquals(1.0, WhiteSimilarity.compareStrings("hello", "hello"), 0.01);
    }

    @Test
    public void equalStringsCaseInsensitive() {
        assertEquals(1.0, WhiteSimilarity.compareStrings("HeLlO", "hello"), 0.01);
    }

    @Test
    public void equalsSingleLetter() {
        assertEquals(1.0, WhiteSimilarity.compareStrings("h", "h"), 0.01);
    }

    @Test
    public void notEqualsSingleLetter() {
        assertEquals(0.0, WhiteSimilarity.compareStrings("a", "h"), 0.01);
    }

    @Test
    public void almostEquals() {
        assertTrue(WhiteSimilarity.compareStrings("hello", "helllo") >= 0.8);
        assertTrue(WhiteSimilarity.compareStrings("hello", "HeLLlo") >= 0.8);
    }

    @Test
    public void noIntersection() {
        assertEquals(0.0, WhiteSimilarity.compareStrings("hi mom", "bye dad"), 0.01);
    }
}
