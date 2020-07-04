package model.query;

import model.Word;

public interface IInfoQuery {
    Word getMeaning(String term);
}
