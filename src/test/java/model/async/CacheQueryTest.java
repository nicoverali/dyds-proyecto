package model.async;

import model.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheQueryTest {

    @DisplayName("Cache Query with a specific term")
    @Test
    public void shouldQueryDbAndReturnWord(){

        IInfoQuery cacheQuery = CacheQuery.getInstance();

        Word answerWord = cacheQuery.getMeaningWord("prueba");

        String previosKnownMeaning = "Meaning de prueba";

        assertEquals(previosKnownMeaning,answerWord.getMeaning());

    }

}