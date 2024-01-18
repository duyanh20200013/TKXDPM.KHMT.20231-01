package media_wizard;

import controller.IAdminItemAdvice;
import domain.MediaRepo;
import form_controller_impl.InputFormFieldControllerImpl;
import form_controller_impl.Validators;
import ui.form.quickndirty.InputFormField;
import ui.form.quickndirty.QuickFormUI;
import ui.wizard.custom.BaseWizardScreen;
import ui.wizard.custom.DynamicWizardScreen;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class MediaEditPriceWizard extends BaseWizardScreen implements DynamicWizardScreen {
    private final QuickFormUI quickFormUI;
    private final InputFormField price;
    private final InputFormFieldControllerImpl priceController;
    private final MediaEditState state;
    private final MediaRepo mediaRepo;
    private final IAdminItemAdvice advice;

    public MediaEditPriceWizard(MediaEditState mediaCreateState, MediaRepo mediaRepo, IAdminItemAdvice advice) {
        this.mediaRepo = mediaRepo;
        state = mediaCreateState;
        this.advice = advice;
        long originalPrice = Long.parseLong(mediaCreateState.getPrice());
        priceController = new InputFormFieldControllerImpl("price", Validators.ofAll(Validators.notBlank("Required"), Validators.positiveNumber("Positive number"), s->{
            long l = Long.parseLong(s);
            if(l < 0.3*originalPrice || l>1.5*originalPrice)
                return "Too much price change, must in [30%, 150%] original";
            return null;
        }));
        priceController.setValue(state.getPrice());
        price = new InputFormField("price", 0);
        price.setController(priceController);
        quickFormUI = new QuickFormUI("Price change", List.of(price));
    }

    @Override
    public Component getComponent() {
        return quickFormUI;
    }

    @Override
    public String getTitle() {
        return "Change price of "+state.getTitle();
    }

    @Override
    public void initBeforeDisplay(Map<String, Object> argument) {

    }

    @Override
    public void init() {
    }

    @Override
    public void beforeNext() {
        state.setPrice(priceController.getValue());
    }

    @Override
    public void beforeBack() {
    }

    @Override
    public boolean canNext() {
        return priceController.getError().isEmpty();
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public DynamicWizardScreen getNextScreen() {
        return new MediaEditConfirm(state, mediaRepo, advice, true);
    }

    @Override
    public boolean hasNext() {
        return true;
    }
}
