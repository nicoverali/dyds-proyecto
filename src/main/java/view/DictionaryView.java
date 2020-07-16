package view;

import controller.IDictionaryController;
import model.IWordModel;
import model.IWordModelListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DictionaryView implements IDictionaryView {

  private PlaceholdableTextField userWordTextField;
  private JButton goButton;
  private JPanel contentPane;
  private JMeaningTextPane meaningTextPane;

  private final IDictionaryController dictController;
  private final IWordModel dictModel;

  public DictionaryView(IDictionaryController controller, IWordModel model){
    dictController = controller;
    dictModel = model;
    initJElements();
    initListeners();
  }

  public JPanel getContentPane(){
    return contentPane;
  }

  private void initJElements(){
    userWordTextField.setPlaceholder("Write a word ...");
  }

  private void initListeners() {
    goButton.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        dictController.onUserWordSearch(userWordTextField.getText());
      }
    });

    dictModel.setListener(new IWordModelListener(){
      @Override
      public void onWordUpdate() {
        meaningTextPane.setWordMeaning(dictModel.getWord());
      }

      @Override
      public void onWordNotFound() {
        meaningTextPane.setWordNotFound();
      }
    });
  }
}
