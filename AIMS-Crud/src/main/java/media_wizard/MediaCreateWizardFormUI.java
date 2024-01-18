package media_wizard;

import lombok.Getter;
import ui.form.quickndirty.FileSelector;
import ui.form.quickndirty.InputFormField;
import ui.form.quickndirty.QuickFormUI;
import ui.form.quickndirty.SelectFormField;

import java.util.List;

public class MediaCreateWizardFormUI {
    @Getter
    private final QuickFormUI quickFormUI;
    private final InputFormField category, title, value, price, quality;
    private final FileSelector imageUrl;
    private final SelectFormField type;

    public MediaCreateWizardFormUI() {
        int row = 0;
        type = new SelectFormField("type", List.of("cd", "book", "dvd"), row++);
        category = new InputFormField("category", row++);
        title = new InputFormField("title", row++);
        value = new InputFormField("value", row++);
        price = new InputFormField("price", row++);
        quality = new InputFormField("quality", row++);
        imageUrl = new FileSelector("imageUrl", row);
        quickFormUI = new QuickFormUI("Media create form", List.of(
                type, category, title, value, price, quality, imageUrl
        ));
    }

    public void setController(MediaStateFormGroup formGroup) {
        category.setController(formGroup.getCategory());
        title.setController(formGroup.getTitle());
        value.setController(formGroup.getValue());
        price.setController(formGroup.getPrice());
        quality.setController(formGroup.getQuantity());
        imageUrl.setController(formGroup.getImageUrl());
        type.setController(formGroup.getType());
    }
}
