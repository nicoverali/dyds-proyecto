package model;

public interface IWordModel {
    Word getWord();
    void setListener(IWordModelListener listener);
    void searchWord(String term);
}
