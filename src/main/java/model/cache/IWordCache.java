package model.cache;

import model.Word;

public interface IWordCache {

    Word get(String term);

    void save(Word word);

}
