package view;

import controller.IDictionaryController;
import model.IWordModel;
import model.IWordModelListener;
import model.Word;

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

  public JPanel getContentPane(){
    return contentPane;
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
        Word newWord = dictModel.getWord();
        String meaningWithBold = textToHtml(newWord.getMeaning(), new String[]{newWord.getTerm()});
        wordMeaningPanel.setText(meaningWithBold);
      }

      @Override
      public void onWordNotFound() {
        wordMeaningPanel.setText("Sorry, no result.");
      }
    });
  }

  private String textToHtml(String text, String[] boldWords){
    StringBuilder builder =  new StringBuilder();
    builder.append("<font face=\"arial\">");

    String boldWordsRegex = "(?i)("+String.join("|", boldWords)+")";
    String textWithBold = text.replaceAll(boldWordsRegex, "<b>$1</b>");

    builder.append(textWithBold);

    builder.append("</font>");
    return builder.toString();
  }

}
