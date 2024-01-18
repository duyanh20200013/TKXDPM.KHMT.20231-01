package media_wizard;

import controller.IAdminItemAdvice;
import domain.MediaRepo;
import ui.wizard.custom.BaseWizardScreen;
import ui.wizard.custom.DynamicWizardScreen;

import java.awt.*;
import java.util.Map;

public class MediaEditWizard extends BaseWizardScreen implements DynamicWizardScreen {
    protected final MediaEditState mediaCreateState;
    protected final MediaEditWizardFormUI mediaCreateWizardFormUI = new MediaEditWizardFormUI();
    protected final MediaStateFormGroup mediaStateFormGroup;
    protected final MediaRepo mediaRepo;
    private final IAdminItemAdvice advice;

    public MediaEditWizard(MediaEditState mediaCreateState, MediaRepo mediaRepo, IAdminItemAdvice advice) {
        this.mediaRepo = mediaRepo;
        this.mediaCreateState = mediaCreateState;
        mediaStateFormGroup = new MediaStateFormGroup("Media create form", mediaCreateState);
        this.advice = advice;
        mediaStateFormGroup.getFormGroupController().subscribeValueChange(this::notifyStateChange);
        mediaCreateWizardFormUI.setController(mediaStateFormGroup);
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
        return new MediaEditConfirm(mediaCreateState, mediaRepo, advice, false);
    }

    @Override
    public boolean hasNext() {
        return true;
    }
}
