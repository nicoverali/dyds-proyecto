package model.query;

public interface IInfoQueryAsync {
    void getMeaning(String term, IInfoQueryListener listener);
}
