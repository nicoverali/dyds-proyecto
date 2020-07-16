package view;

import model.Word;

import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;

public class JMeaningTextPane extends JTextPane {

    private static final String NOT_FOUND_HTML_TEMPLATE =   "<div style=\"text-align:center;margin-top:80px\">\n" +
                                                                "<img src=\"%s\"/>" +
                                                                "<p style=\"font-family:'Open Sans'; font-size:14px\">\n" +
                                                                "    <b>Sorry! We couldn't find that word</b>\n" +
                                                                "   <br>Try to find another one!\n"+
                                                                "</p>\n" +
                                                            "</div>";

    private final String notFoundHtml;

    public JMeaningTextPane() throws URISyntaxException {
        super();
        URI notFoundImageURI = this.getClass().getResource("/images/nothing-found.png").toURI();
        notFoundHtml = String.format(NOT_FOUND_HTML_TEMPLATE, notFoundImageURI.toString());
    }

    public void setWordMeaning(Word word){
        String meaningWithBold = addBoldTo(word.getTerm(), word.getMeaning());
        this.setText(decorateText(meaningWithBold));
    }

    public void setWordNotFound(){
        this.setText(notFoundHtml);
    }

    private String addBoldTo(String word, String text){
        String boldWordRegex = "\\b(?i)("+word+"s?)\\b";
        return text.replaceAll(boldWordRegex, "<b>$1</b>");
    }

    private String decorateText(String text){
        return "<font face=\"Open Sans\" size=+1>" +
                text +
                "</font>";
    }



}
