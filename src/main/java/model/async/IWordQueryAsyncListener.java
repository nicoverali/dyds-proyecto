package model.async;

import model.Word;

public interface IWordQueryAsyncListener {
    void onSuccess(Word word);

    void onFailure();
}
