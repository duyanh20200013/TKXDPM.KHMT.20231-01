package ui.wizard.custom;

import utils.IdMapped;
import utils.Subscription;

public abstract class BaseWizardScreen implements WizardScreen {
    private final IdMapped<Runnable> runnableIdMapped = new IdMapped<>();
    protected void notifyStateChange() {
        runnableIdMapped.foreach(Runnable::run);
    }

    @Override
    public Subscription stateChange(Runnable runnable) {
        var id = runnableIdMapped.addObj(runnable);
        return ()->runnableIdMapped.removeByKey(id);
    }
}
