package ui.form.quickndirty;

import ui.form.base.InputFormFieldController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.Arrays;

public class FileSelector extends BaseTextFormField {
    private final JPanel field;
    private final JButton btn;

    public FileSelector(String name, int row) {
        super(new JLabel(name), new JPanel(), new JTextField(), new JLabel(), row);
        field = (JPanel) super.content;
        field.setLayout(new BorderLayout());
        field.add(super.textField);
        btn = new JButton("...");
        field.add(btn, BorderLayout.LINE_END);
    }

    @Override
    public void setController(InputFormFieldController controller) {
        Arrays.stream(btn.getActionListeners()).forEach(btn::removeActionListener);
        super.setController(controller);
        btn.addActionListener(ev->{
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(field);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                controller.setValue(chooser.getSelectedFile().getPath());
            }
        });
    }
}
