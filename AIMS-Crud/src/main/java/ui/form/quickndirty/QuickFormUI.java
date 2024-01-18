package ui.form.quickndirty;

import ui.form.base.FormController;
import ui.form.base.FormGroupController;
import ui.form.base.InputFormFieldController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickFormUI extends FormUI {
    public QuickFormUI(String formName, List<BaseFormField> formElements) {
        super(formName);
        var controllerMap = new HashMap<String, FormController>();
        formElements.forEach(v->{
            v.addToGridBagLayout(super.formList);
        });
    }
}
