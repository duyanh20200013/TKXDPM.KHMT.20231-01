package ui.wizard;

import ui.wizard.custom.WizardScreen;
import utils.Subscription;

public interface IWizardSequence {
    WizardScreen getCurrent();

    void next();

    void back();

    boolean canBack();

    boolean canNext();
    boolean isFinalScreen();

    Subscription stateChange(Runnable runnable);
}
