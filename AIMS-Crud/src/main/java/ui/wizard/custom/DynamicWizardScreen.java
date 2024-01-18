package ui.wizard.custom;

public interface DynamicWizardScreen extends WizardScreen {
    DynamicWizardScreen getNextScreen();
    boolean hasNext();
}
