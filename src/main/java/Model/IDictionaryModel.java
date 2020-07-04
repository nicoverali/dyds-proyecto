package Model;

public interface IDictionaryModel {

    String getLastWordMeaning();

    void updateLastWordMeaning(String userWord);

    void setModelListener(IDictionaryModelListener listener);

}
