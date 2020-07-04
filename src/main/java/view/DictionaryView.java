package view;

import Model.IDictionaryModel;
import Model.IDictionaryModelListener;
import controller.IDictionaryController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DictionaryView implements IDictionaryView {
  private JTextField userWordTextPanel;
  private JButton goButton;
  private JPanel contentPane;
  private JTextPane wordMeaningPanel;

  private final IDictionaryController dictController;
  private final IDictionaryModel dictModel;

  public DictionaryView(IDictionaryController controller, IDictionaryModel model){
    dictController = controller;
    dictModel = model;
    initJElements();
    initListeners();
  }

  private void initJElements(){
    wordMeaningPanel.setContentType("text/html");
  }

  private void initListeners() {
    goButton.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        dictController.onUserWordSearch(userWordTextPanel.getText());
      }
    });

    dictModel.setModelListener(new IDictionaryModelListener(){
      @Override
      public void onLastMeaningUpdate() {
        wordMeaningPanel.setText(dictModel.getLastWordMeaning());    
      }
    });
  }

  public JPanel getContentPane(){
    return contentPane;
  }
}
