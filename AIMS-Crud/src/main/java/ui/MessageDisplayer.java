package ui;

import javax.swing.*;
import java.awt.*;

public class MessageDisplayer {
    private final Component parent;

    public MessageDisplayer(Component topLevelFrame) {
        this.parent = topLevelFrame;
    }

    public void displayInformation(String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayCriticalError(String s) {
        JOptionPane.showMessageDialog(parent, s, "Critical Failure", JOptionPane.ERROR_MESSAGE);
    }

    public boolean confirmDialog(String message) {
        int ret = JOptionPane.showConfirmDialog(parent, message, "Confirm action", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        return ret == JOptionPane.OK_OPTION;
    }
}
