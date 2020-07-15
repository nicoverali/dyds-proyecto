package controller;


import model.IWordModel;

public class DictionaryController implements IDictionaryController {

    private final IWordModel dictModel;

    public DictionaryController(IWordModel model){
        dictModel = model;
    }

    @Override
    public void onUserWordSearch(String word) {
        if(isValidWord(word)){
            dictModel.searchWord(word);
        }
    }

    private boolean isValidWord(String word){
        return word != null && !word.isEmpty();
    }
}
