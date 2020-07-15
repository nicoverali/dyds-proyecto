import view.DictionaryViewModule;
import view.IDictionaryView;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        DictionaryViewModule viewModule = DictionaryViewModule.getInstance();
        IDictionaryView dictView = viewModule.getDictionaryView();

        JFrame frame = new JFrame("Online Dictionary");
        frame.setContentPane(dictView.getContentPane());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
