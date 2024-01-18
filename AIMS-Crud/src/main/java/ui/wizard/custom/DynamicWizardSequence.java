package ui.wizard.custom;

import ui.wizard.IWizardSequence;
import utils.IdMapped;
import utils.Subscription;

import java.util.Stack;

public class DynamicWizardSequence implements IWizardSequence {
    private final Stack<DynamicWizardScreen> screens = new Stack<>();
    private final IdMapped<Runnable> runnableIdMapped = new IdMapped<>();
    private Subscription subscription;
    public DynamicWizardSequence(DynamicWizardScreen root) {
        screens.push(root);
    }

    @Override
    public DynamicWizardScreen getCurrent() {
        return screens.peek();
    }

    @Override
    public void next() {
        if(subscription != null) subscription.close();
        if(getCurrent().hasNext()) {
            getCurrent().beforeNext();
            screens.push(getCurrent().getNextScreen());
            getCurrent().init();
            subscription = getCurrent().stateChange(()->runnableIdMapped.foreach(Runnable::run));
        }
        else throw new IllegalStateException();
    }

    @Override
    public void back() {
        if(screens.size() <= 1) throw new IllegalStateException();
        getCurrent().beforeBack();
        if(subscription != null) subscription.close();
        screens.pop();
        getCurrent().init();
        subscription = getCurrent().stateChange(()->runnableIdMapped.foreach(Runnable::run));
    }

    @Override
    public boolean canBack() {
        return getCurrent().canBack();
    }

    @Override
    public boolean canNext() {
        return getCurrent().canNext();
    }

    @Override
    public boolean isFinalScreen() {
        return !getCurrent().hasNext();
    }

    @Override
    public Subscription stateChange(Runnable runnable) {
        int ret = runnableIdMapped.addObj(runnable);
        return ()->{
           runnableIdMapped.removeByKey(ret);
        };
    }
}
