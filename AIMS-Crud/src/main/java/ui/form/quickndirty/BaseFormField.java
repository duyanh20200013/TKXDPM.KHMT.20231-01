package ui.form.quickndirty;

import ui.form.base.FormController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BaseFormField {
    protected final JLabel name;
    protected final JComponent content;
    protected final JLabel errorLabel;
    protected final int row;

    protected BaseFormField(JLabel name, JComponent content, JLabel errorLabel, int row) {
        this.name = name;
        this.content = content;
        this.errorLabel = errorLabel;
        this.row = row;
        init();
    }

    protected void init() {
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Times new roman", Font.BOLD, 12));
        name.setFont(new Font("Times new roman", Font.PLAIN, 15));
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    void addToGridBagLayout(JComponent component) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = row * 2;
        component.add(errorLabel, c);
        c.gridx = 0;
        ++c.gridy;
        c.insets = new Insets(0, 0,0, 20);
        component.add(name, c);

        c.gridx = 1;
        c.weightx = 1.0;
        c.insets = new Insets(10, 0, 10, 10);
        component.add(content, c);
    }
}
