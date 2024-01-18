package media_wizard;

import domain.Media;
import domain.MediaRepo;
import ui.wizard.custom.BaseWizardScreen;
import ui.wizard.custom.DynamicWizardScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ConfirmScreen extends BaseWizardScreen implements DynamicWizardScreen {
    protected final JLabel jLabel = new JLabel("Confirm create media");
    protected final JLabel content = new JLabel();
    protected final JPanel jPanel = new JPanel();
    protected final MediaCreateState state;
    protected final MediaRepo mediaRepo;
    protected final JLabel errorLabel = new JLabel();
    protected boolean proceed = false;

    public ConfirmScreen(MediaCreateState state, MediaRepo mediaRepo) {
        this.state = state;
        this.mediaRepo = mediaRepo;
        content.setText("<html>"
                +"Please add budget :>"
                +"</html>");
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
        jPanel.add(jLabel, BorderLayout.PAGE_START);
        var btn = new JButton("Confirm");
        btn.addActionListener(ac->onSubmit());
        jPanel.add(btn);
        jPanel.add(errorLabel);
    }

    protected void onSubmit() {
        try {
            var media = new Media(state);
            mediaRepo.save(media);
            proceed = true;
            notifyStateChange();
            errorLabel.setText("Success, please click 'Next'");
        } catch (Exception exception) {
            errorLabel.setText(exception.getMessage());
        }
    }

    @Override
    public Component getComponent() {
        return jPanel;
    }

    @Override
    public String getTitle() {
        return "Confirm create media";
    }

    @Override
    public void initBeforeDisplay(Map<String, Object> argument) {

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
        return proceed;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public DynamicWizardScreen getNextScreen() {
        return new ResultScreen();
    }

    @Override
    public boolean hasNext() {
        return true;
    }
}
