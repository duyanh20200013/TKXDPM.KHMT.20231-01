package media_wizard;

import ui.wizard.custom.BaseWizardScreen;
import ui.wizard.custom.DynamicWizardScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ResultScreen extends BaseWizardScreen implements DynamicWizardScreen {
    @Override
    public Component getComponent() {
        return new JLabel("Finished...");
    }

    @Override
    public String getTitle() {
        return "Done";
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
        return true;
    }

    @Override
    public boolean canBack() {
        return false;
    }

    @Override
    public DynamicWizardScreen getNextScreen() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }
}
