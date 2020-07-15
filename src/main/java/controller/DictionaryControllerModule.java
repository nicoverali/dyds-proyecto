package controller;

import model.WordModelModule;

public class DictionaryControllerModule {

    private static DictionaryControllerModule instance;
    private IDictionaryController dictionaryController;

    private DictionaryControllerModule() {
        WordModelModule modelModule = WordModelModule.getInstance();
        dictionaryController = new DictionaryController(modelModule.getWordModel());
    }

    public static DictionaryControllerModule getInstance() {
        if(instance == null) {
            instance =  new DictionaryControllerModule();
        }
        return instance;
    }

    public IDictionaryController getDictionaryController(){
        return dictionaryController;
    }


}
