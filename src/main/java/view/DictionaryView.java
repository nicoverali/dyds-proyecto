package view;

import controller.IDictionaryController;
import model.IWordModel;
import model.IWordModelListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DictionaryView implements IDictionaryView {
  private JTextField userWordTextPanel;
  private JButton goButton;
  private JPanel contentPane;
  private JTextPane wordMeaningPanel;

  private final IDictionaryController dictController;
  private final IWordModel dictModel;

  public DictionaryView(IDictionaryController controller, IWordModel model){
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

    dictModel.setListener(new IWordModelListener(){
      @Override
      public void onWordUpdate() {
        wordMeaningPanel.setText(dictModel.getWord().getMeaning());
      }

      @Override
      public void onWordNotFound() {

      }
    });
  }

  public JPanel getContentPane(){
    return contentPane;
  }
}
