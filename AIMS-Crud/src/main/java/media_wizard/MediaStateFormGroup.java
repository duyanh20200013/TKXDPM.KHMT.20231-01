package media_wizard;

import form_controller_impl.FormGroupControllerImpl;
import form_controller_impl.InputFormFieldControllerImpl;
import form_controller_impl.Validators;
import lombok.Getter;

import java.util.List;

public class MediaStateFormGroup {
    @Getter
    private final InputFormFieldControllerImpl type, category, title, value, imageUrl, price, quantity;
    @Getter
    private final FormGroupControllerImpl formGroupController;

    public MediaStateFormGroup(String formName, MediaCreateState state) {
        type = new InputFormFieldControllerImpl("type", Validators.notBlank("Not blank"));
        category = new InputFormFieldControllerImpl("category", Validators.notBlank("Not blank"));
        title = new InputFormFieldControllerImpl("title", Validators.notBlank("Not blank"));
        value = new InputFormFieldControllerImpl("value", Validators.ofAll(Validators.notBlank("Not blank"), Validators.positiveNumber("Positive number")));
        imageUrl = new InputFormFieldControllerImpl("imageUrl", Validators.notBlank("Not blank"));
        price = new InputFormFieldControllerImpl("price", Validators.ofAll(Validators.notBlank("Not blank"), Validators.positiveNumber("Positive number")));
        quantity = new InputFormFieldControllerImpl("quantity", Validators.ofAll(Validators.notBlank("Not blank"), Validators.positiveNumber("Positive number")));
        formGroupController = new FormGroupControllerImpl(formName, List.of(category, title, value, imageUrl, price, quantity));

        type.setValue(state.getType());
        category.setValue(state.getCategory());
        title.setValue(state.getTitle());
        value.setValue(state.getValue());
        imageUrl.setValue(state.getImageUrl());
        price.setValue(state.getPrice());
        quantity.setValue(state.getQuantity());
    }

    public void update(MediaCreateState mediaCreateState) {
        mediaCreateState.setType(type.getValue());
        mediaCreateState.setCategory(category.getValue());
        mediaCreateState.setTitle(title.getValue());
        mediaCreateState.setValue(value.getValue());
        mediaCreateState.setImageUrl(imageUrl.getValue());
        mediaCreateState.setPrice(price.getValue());
        mediaCreateState.setQuantity(quantity.getValue());
    }
}
