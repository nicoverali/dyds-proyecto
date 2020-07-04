package controller;

import Model.IDictionaryModel;

public class DictionaryController implements IDictionaryController {

    private final IDictionaryModel dictModel;

    public DictionaryController(IDictionaryModel model){
        dictModel = model;
    }

    @Override
    public void onUserWordSearch(String word) {
        dictModel.updateLastWordMeaning(word);
    }
}
