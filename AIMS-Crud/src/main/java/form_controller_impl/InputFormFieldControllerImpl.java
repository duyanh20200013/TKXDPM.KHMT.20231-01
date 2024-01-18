package form_controller_impl;

import ui.form.base.InputFormFieldController;

import java.util.Optional;
import java.util.function.Function;

public class InputFormFieldControllerImpl extends FormControllerImpl implements InputFormFieldController {
    private final Function<String, String> validator;
    private String error;
    protected String value;
    public InputFormFieldControllerImpl(String name, Function<String, String> validator) {
        super(name );
        this.validator = validator;
    }

    @Override
    public Optional<String> getError() {
        return Optional.ofNullable(error);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        if(value != null && !value.equals(this.value)) {
            error = validator.apply(value);
            this.value = value;
            valueChange.foreach(Runnable::run);
        }
    }
}
