package ui.wizard;

import utils.Subscription;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class WizardTopLevel {
    private final JDialog jDialog;
    private final JButton backButton, nextButton, cancelButton;
    private final JPanel mainPanel;
    public WizardTopLevel(JFrame parent) {
        jDialog = new JDialog(parent, true);

        JPanel buttonPanel = new JPanel();
        Box buttonBox = new Box(BoxLayout.X_AXIS);
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));

        backButton = new JButton("Back");
        nextButton = new JButton("Next");
        cancelButton = new JButton("Cancel");

        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(new JSeparator(), BorderLayout.NORTH);

        buttonBox.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
        buttonBox.add(backButton);
        buttonBox.add(Box.createHorizontalStrut(10));
        buttonBox.add(nextButton);
        buttonBox.add(Box.createHorizontalStrut(30));
        buttonBox.add(cancelButton);
        buttonPanel.add(buttonBox, BorderLayout.EAST);
        jDialog.add(buttonPanel, BorderLayout.SOUTH);
        jDialog.add(mainPanel, BorderLayout.CENTER);
        jDialog.setPreferredSize(new Dimension(800, 800));
        jDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelButton.doClick();
            }
        });
    }

    private void updateState(IWizardSequence wizardSequence) {
        backButton.setEnabled(wizardSequence.canBack());
        nextButton.setEnabled(wizardSequence.canNext());
        if(wizardSequence.isFinalScreen()) {
            nextButton.setText("Finish");
        }
        else nextButton.setText("Next");
        mainPanel.removeAll();
        wizardSequence.getCurrent().getComponent().setVisible(true);
        mainPanel.add(wizardSequence.getCurrent().getComponent());
        mainPanel.invalidate();
        jDialog.setTitle(wizardSequence.getCurrent().getTitle());
        jDialog.validate();
        jDialog.pack();
        jDialog.repaint();
    }

    public void run(IWizardSequence wizardSequence) {
        Semaphore semaphore = new Semaphore(0);
        Subscription subscription = wizardSequence.stateChange(()->updateState(wizardSequence));
        backButton.addActionListener(ev->{
            wizardSequence.back();
            updateState(wizardSequence);
        });
        nextButton.addActionListener(ev->{
            if(wizardSequence.isFinalScreen()) {
                subscription.close();
                jDialog.dispose();
                semaphore.release();
            }
            else {
                wizardSequence.next();
                updateState(wizardSequence);
            }
        });
        cancelButton.addActionListener(ev->{
            subscription.close();
            jDialog.dispose();
            semaphore.release();
        });
        updateState(wizardSequence);
        jDialog.setVisible(true);

        Arrays.stream(nextButton.getActionListeners()).forEach(nextButton::removeActionListener);
        Arrays.stream(backButton.getActionListeners()).forEach(backButton::removeActionListener);

        try {
            semaphore.acquire();
        } catch (InterruptedException interruptedException) {
        }
    }
}
