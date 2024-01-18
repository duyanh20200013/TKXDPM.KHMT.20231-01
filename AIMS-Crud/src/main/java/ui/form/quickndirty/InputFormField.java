package ui.form.quickndirty;

import ui.form.base.InputFormFieldController;
import utils.Subscription;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class InputFormField extends BaseTextFormField {
    public InputFormField(String name, int row) {
        super(new JLabel(name), new JTextField(), new JLabel(), row);
    }
}

