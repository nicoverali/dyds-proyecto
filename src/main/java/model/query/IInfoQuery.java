package model.query;

import model.Word;

public interface IInfoQuery {
    Word getMeaningWord(String term);
}
