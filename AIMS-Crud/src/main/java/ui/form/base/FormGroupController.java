package ui.form.base;

import utils.Subscription;

import java.util.List;

public interface FormGroupController {
    String getFormName();
    List<? extends FormController> getFieldList();
    boolean isValid();
    Subscription subscribeValueChange(Runnable runnable);
}
