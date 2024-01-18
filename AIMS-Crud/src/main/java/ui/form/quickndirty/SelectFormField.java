package ui.form.quickndirty;

import ui.form.base.InputFormFieldController;
import utils.Subscription;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

public class SelectFormField extends BaseFormField {
    private Subscription controllerValueChange = null;
    private Subscription documentValueChange = null;
    private final DefaultComboBoxModel<String> model;

    private final JComboBox<String> cb;
    public SelectFormField(String name, List<String> options, int row) {
        super(new JLabel(name), new JComboBox<>(new DefaultComboBoxModel<>(new Vector<>(options))), new JLabel(), row);
        this.cb = (JComboBox<String>)content;
        cb.setEditable(false);
        model = (DefaultComboBoxModel<String>)cb.getModel();
    }

    public void setController(InputFormFieldController controller) {
        if(controllerValueChange != null)
            controllerValueChange.close();
        if(documentValueChange != null)
            documentValueChange.close();

        model.setSelectedItem(controller.getValue());
        documentValueChange = new MyListener(controller);
        controllerValueChange = controller.subscribeValueChange(()->{
            SwingUtilities.invokeLater(()->{
                if(!model.getSelectedItem().equals(controller.getValue()))
                    model.setSelectedItem(controller.getValue());
                var opt = controller.getError();
                if(opt.isPresent()) {
                    errorLabel.setText(opt.get());
                }
                else errorLabel.setText("");
            });
        });
    }

    private final class MyListener implements Subscription, ItemListener {
        private final InputFormFieldController controller;

        private MyListener(InputFormFieldController controller) {
            cb.addItemListener(this);
            this.controller = controller;
        }


        @Override
        public void close() {
            cb.removeItemListener(this);
        }

        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            controller.setValue((String)model.getSelectedItem());
        }
    }
}
