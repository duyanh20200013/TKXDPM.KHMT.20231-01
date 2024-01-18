package ui.form.quickndirty;

import ui.form.base.FormGroupController;

import javax.swing.*;
import java.awt.*;

public class FormUI extends JPanel {
    protected final JPanel formList = new JPanel(new GridBagLayout());

    public FormUI(String name) {
        super(new BorderLayout());

        JLabel title = new JLabel(name);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Times new roman", Font.BOLD, 25));
        add(title, BorderLayout.PAGE_START);
        add(formList, BorderLayout.CENTER);
    }
}
