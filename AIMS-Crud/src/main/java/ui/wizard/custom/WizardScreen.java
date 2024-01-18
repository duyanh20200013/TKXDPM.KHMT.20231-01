package ui.wizard.custom;

import ui.Screen;
import utils.Subscription;

public interface WizardScreen extends Screen {
    void init();
    void beforeNext();
    void beforeBack();
    boolean canNext();
    Subscription stateChange(Runnable runnable);
    boolean canBack();
}
