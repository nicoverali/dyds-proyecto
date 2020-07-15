package view;

import controller.DictionaryControllerModule;
import model.WordModelModule;

public class DictionaryViewModule {

    private static DictionaryViewModule instance;
    private IDictionaryView dictionaryView;

    private DictionaryViewModule() {
        WordModelModule modelModule = WordModelModule.getInstance();
        DictionaryControllerModule controllerModule = DictionaryControllerModule.getInstance();
        dictionaryView = new DictionaryView(controllerModule.getDictionaryController(), modelModule.getWordModel());
    }

    public static DictionaryViewModule getInstance() {
        if(instance == null) {
            instance =  new DictionaryViewModule();
        }
        return instance;
    }

    public IDictionaryView getDictionaryView(){
        return dictionaryView;
    }


}
