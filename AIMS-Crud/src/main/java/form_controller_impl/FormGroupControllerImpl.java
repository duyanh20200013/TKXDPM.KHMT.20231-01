package form_controller_impl;

import lombok.Getter;
import ui.form.base.FormController;
import ui.form.base.FormGroupController;
import utils.IdMapped;
import utils.Subscription;

import java.util.List;
import java.util.Optional;

public class FormGroupControllerImpl implements FormGroupController {
    @Getter
    private final String formName;
    private final List<FormController> fieldList;
    protected final IdMapped<Runnable> valueChange = new IdMapped<>();

    public FormGroupControllerImpl(String formName, List<FormController> fieldList) {
        this.formName = formName;
        this.fieldList = fieldList;
        fieldList.forEach(i->i.subscribeValueChange(()->valueChange.foreach(Runnable::run)));
    }

    @Override
    public List<FormController> getFieldList() {
        return fieldList;
    }

    @Override
    public boolean isValid() {
        return fieldList.stream().map(FormController::getError).map(Optional::isEmpty).reduce(Boolean::logicalAnd).orElse(true);
    }

    @Override
    public Subscription subscribeValueChange(Runnable runnable) {
        var id = valueChange.addObj(runnable);
        return () -> valueChange.removeByKey(id);
    }
}
