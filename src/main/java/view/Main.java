package view;

import Model.IDictionaryModel;
import controller.DictionaryController;
import controller.IDictionaryController;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
       IDictionaryModel dictModel = (IDictionaryModel) new Object();
       IDictionaryController dictController = new DictionaryController(dictModel);
       IDictionaryView dictView = new DictionaryView(dictController, dictModel);

       JFrame frame = new JFrame("Online Dictionary");
       frame.setContentPane(dictView.getContentPane());
       frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       frame.pack();
       frame.setVisible(true);
    }

}
