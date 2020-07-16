package view;

import controller.DictionaryControllerModule;
import model.WordModelModule;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DictionaryViewModule {

    private static DictionaryViewModule instance;
    private IDictionaryView dictionaryView;

    private DictionaryViewModule() {
        WordModelModule modelModule = WordModelModule.getInstance();
        DictionaryControllerModule controllerModule = DictionaryControllerModule.getInstance();
        dictionaryView = new DictionaryView(controllerModule.getDictionaryController(), modelModule.getWordModel());

        try {
            loadFonts();
        } catch (Exception e) {
            System.out.println("Fonts couldn't be loaded");
        }
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

    public void loadFonts() throws IOException, FontFormatException, URISyntaxException {
        Font poppins = Font.createFont(Font.TRUETYPE_FONT, getResourceAsFile("/fonts/Poppins-Regular.ttf"));
        Font openSans = Font.createFont(Font.TRUETYPE_FONT, getResourceAsFile("/fonts/OpenSans-Regular.ttf"));
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(poppins);
        ge.registerFont(openSans);

    }

    private File getResourceAsFile(String path) throws URISyntaxException {
        URI resURI = this.getClass().getResource(path).toURI();
        return new File(resURI);
    }

}
