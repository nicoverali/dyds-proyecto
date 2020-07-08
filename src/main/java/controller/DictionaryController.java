package controller;


import model.IWordModel;

public class DictionaryController implements IDictionaryController {

    private final IWordModel dictModel;

    public DictionaryController(IWordModel model){
        dictModel = model;
    }

    @Override
    public void onUserWordSearch(String word) {
        dictModel.searchWord(word);
    }
}
