package model.async;

public interface IWordQueryAsync {
    void getMeaningWord(String term, IWordQueryAsyncListener listener);
}
