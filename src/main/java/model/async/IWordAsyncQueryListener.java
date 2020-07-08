package model.async;

import model.Word;

public interface IWordAsyncQueryListener {
    void onWordResult(Word word);
}
