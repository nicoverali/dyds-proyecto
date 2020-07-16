package view;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholdableTextField extends JTextField {

    private String placeholdText = "";

    public PlaceholdableTextField(){
        super();
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (PlaceholdableTextField.this.getText().equals(placeholdText)) {
                    PlaceholdableTextField.this.setText("");
                    PlaceholdableTextField.this.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (PlaceholdableTextField.this.getText().isEmpty()) {
                    PlaceholdableTextField.this.setForeground(Color.GRAY);
                    PlaceholdableTextField.this.setText(placeholdText);
                }
            }
        });
    }

    public void setPlaceholder(String placeholder){
        placeholdText = placeholder;
    }
}
