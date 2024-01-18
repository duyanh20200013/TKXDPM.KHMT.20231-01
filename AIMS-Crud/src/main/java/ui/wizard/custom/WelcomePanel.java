package ui.wizard.custom;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Map;

public class WelcomePanel extends BaseWizardScreen implements DynamicWizardScreen {
    private final JPanel panel = new JPanel(new BorderLayout());
    private final DynamicWizardScreen next;

    public WelcomePanel(URL banner, String title, String content, DynamicWizardScreen next) {
        this.next = next;
        var ban = new JPanel();
        ban.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(0), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        ban.setBackground(Color.white);
        ban.add(new JLabel(new ImageIcon(banner)));
        panel.add(ban,BorderLayout.LINE_START);
        var con = new JPanel(new BorderLayout());
        var titleLabel = new JLabel(title);
        var contentLabel = new JLabel(content);

        contentLabel.setVerticalAlignment(SwingConstants.TOP);
        con.add(titleLabel, BorderLayout.PAGE_START);
        con.add(contentLabel);
        con.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(con);
    }

    @Override
    public Component getComponent() {
        return panel;
    }

    @Override
    public String getTitle() {
        return "Welcome";
    }

    @Override
    public void initBeforeDisplay(Map<String, Object> argument) {
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public void init() {
    }

    @Override
    public void beforeNext() {
    }

    @Override
    public void beforeBack() {
    }

    @Override
    public boolean canNext() {
        return true;
    }

    @Override
    public boolean canBack() {
        return false;
    }

    @Override
    public DynamicWizardScreen getNextScreen() {
        return next;
    }
}
