import org.junit.Test;
import ui.wizard.custom.DynamicWizardSequence;
import ui.wizard.custom.FixWizardSequence;
import ui.wizard.custom.WizardScreen;
import ui.wizard.WizardTopLevel;
import ui.wizard.custom.WelcomePanel;
import utils.Subscription;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;

public class Form {
    @Test
    public void t() throws InterruptedException {
        var i = new WizardTopLevel(new JFrame());
        var j = new WizardScreen() {
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
            public Subscription stateChange(Runnable runnable) {
                return ()->{};
            }

            @Override
            public Component getComponent() {
                return new JLabel("abc");
            }

            @Override
            public String getTitle() {
                return "title";
            }

            @Override
            public void initBeforeDisplay(Map<String, Object> argument) {

            }
        };
        i.run(new FixWizardSequence(List.of(j)));
        i.run(new DynamicWizardSequence(new WelcomePanel(ClassLoader.getSystemResource("icon/MediaBanner.png"), "Test", "testing 123", null)));
    }
}
