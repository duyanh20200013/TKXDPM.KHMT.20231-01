package ui.form.quickndirty;

import ui.form.base.FormController;

import javax.swing.*;
import java.util.function.Function;

public class PasswordInputFormField extends BaseTextFormField {
    public PasswordInputFormField(String name, int row) {
        super(new JLabel(name), new JPasswordField(), new JLabel(), row);
    }
}
