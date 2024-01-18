package media_wizard;

import controller.AdminItemAdvice;
import controller.IAdminItemAdvice;
import domain.Media;
import domain.MediaRepo;

public class MediaEditConfirm extends ConfirmScreen {
    private final IAdminItemAdvice advice;
    private final boolean isUpdatePrice;
    private final MediaEditState state;
    public MediaEditConfirm(MediaEditState mediaCreateState, MediaRepo mediaRepo, IAdminItemAdvice advice, boolean isUpdatePrice) {
        super(mediaCreateState, mediaRepo);
        this.state = mediaCreateState;
        this.advice = advice;
        this.isUpdatePrice = isUpdatePrice;
        jLabel.setText("Confirm update media");
    }

    protected void onSubmit() {
        try {
            var media = new Media(state);
            media.setId(state.getId());
            mediaRepo.update(media);
            proceed = true;
            notifyStateChange();
            errorLabel.setText("Success, please click 'Next'");
            advice.editLog(media.getId());
            if(isUpdatePrice)
                advice.updatePriceLog(media.getId());
        } catch (Exception exception) {
            errorLabel.setText(exception.getMessage());
        }
    }

}
