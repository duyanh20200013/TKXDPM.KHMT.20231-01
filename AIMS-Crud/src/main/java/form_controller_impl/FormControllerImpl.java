package form_controller_impl;

import lombok.Getter;
import ui.form.base.FormController;
import utils.IdMapped;
import utils.Subscription;

public abstract class FormControllerImpl implements FormController {
    @Getter
    private final String name;
    protected final IdMapped<Runnable> valueChange = new IdMapped<>();

    public FormControllerImpl(String name) {
        this.name = name;
    }

    @Override
    public Subscription subscribeValueChange(Runnable runnable) {
        var id = valueChange.addObj(runnable);
        return () -> valueChange.removeByKey(id);
    }
}
