package ui.form.quickndirty;

import ui.form.base.FormController;
import ui.form.base.InputFormFieldController;
import utils.Subscription;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

public abstract class BaseTextFormField extends BaseFormField {
    protected final JTextComponent textField;
    private Subscription controllerValueChange = null;
    private Subscription documentValueChange = null;

    protected BaseTextFormField(JLabel name, JTextComponent content, JLabel errorLabel, int row) {
        super(name, content, errorLabel, row);
        textField = content;
    }

    protected BaseTextFormField(JLabel name, JComponent component, JTextComponent content, JLabel errorLabel, int row) {
        super(name, component, errorLabel, row);
        textField = content;
    }

    public void setController(InputFormFieldController controller) {
        if(controllerValueChange != null)
            controllerValueChange.close();
        if(documentValueChange != null)
            documentValueChange.close();

        textField.setText(controller.getValue());
        documentValueChange = new MyListener(textField.getDocument(), controller);
        controllerValueChange = controller.subscribeValueChange(()->{
            SwingUtilities.invokeLater(()->{
                if(!textField.getText().equals(controller.getValue()))
                    textField.setText(controller.getValue());
                var opt = controller.getError();
                if(opt.isPresent()) {
                    errorLabel.setText(opt.get());
                }
                else errorLabel.setText("");
            });
        });
        controller.setValue(textField.getText());
    }

    private static final class MyListener implements DocumentListener, Subscription {
        private final Document document;
        private final InputFormFieldController controller;

        private MyListener(Document document, InputFormFieldController controller) {
            this.document = document;
            this.controller = controller;
            document.addDocumentListener(this);
        }
        private void update() {
            try {
                controller.setValue(document.getText(0, document.getLength()));
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            update();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            update();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            update();

        }

        @Override
        public void close() {
            document.removeDocumentListener(this);
        }
    }
}
