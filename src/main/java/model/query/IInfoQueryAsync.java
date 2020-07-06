package model.query;

public interface IInfoQueryAsync {
    void getMeaningWord(String term, IInfoQueryListener listener);
}
