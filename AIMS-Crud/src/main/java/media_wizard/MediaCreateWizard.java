package media_wizard;

import domain.MediaRepo;
import ui.wizard.custom.BaseWizardScreen;
import ui.wizard.custom.DynamicWizardScreen;

import java.awt.*;
import java.util.Map;

public class MediaCreateWizard extends BaseWizardScreen implements DynamicWizardScreen {
    protected final MediaCreateState mediaCreateState;
    protected final MediaCreateWizardFormUI mediaCreateWizardFormUI = new MediaCreateWizardFormUI();
    protected final MediaStateFormGroup mediaStateFormGroup;
    protected final MediaRepo mediaRepo;
    public MediaCreateWizard(MediaCreateState mediaCreateState, MediaRepo mediaRepo) {
        this.mediaRepo = mediaRepo;
        this.mediaCreateState = mediaCreateState;
        mediaStateFormGroup = new MediaStateFormGroup("Media create form", mediaCreateState);
        mediaStateFormGroup.getFormGroupController().subscribeValueChange(this::notifyStateChange);
        mediaCreateWizardFormUI.setController(mediaStateFormGroup);
    }

    public MediaCreateWizard(MediaRepo mediaRepo) {
        this(new MediaCreateState(), mediaRepo);
    }

    @Override
    public Component getComponent() {
        return mediaCreateWizardFormUI.getQuickFormUI();
    }

    @Override
    public String getTitle() {
        return "Media information";
    }

    @Override
    public void initBeforeDisplay(Map<String, Object> argument) {
    }

    @Override
    public void init() {
    }

    @Override
    public void beforeNext() {
        mediaStateFormGroup.update(mediaCreateState);
    }

    @Override
    public void beforeBack() {
    }

    @Override
    public boolean canNext() {
        return mediaStateFormGroup.getFormGroupController().isValid();
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public DynamicWizardScreen getNextScreen() {
        return new ConfirmScreen(mediaCreateState, mediaRepo);
    }

    @Override
    public boolean hasNext() {
        return true;
    }
}
