package ui.screen;

import form_controller_impl.FormGroupControllerImpl;
import form_controller_impl.InputFormFieldControllerImpl;
import form_controller_impl.Validators;
import ui.RandomContentNavigator;
import ui.MessageDisplayer;
import ui.form.quickndirty.InputFormField;
import ui.form.quickndirty.PasswordInputFormField;
import ui.form.quickndirty.QuickFormUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class LoginScreen extends AbstractScreen {
    private final LoginController loginController;
    public LoginScreen(RandomContentNavigator navigator, LoginController loginController, MessageDisplayer messageDisplayer) {
        super(navigator);
        this.loginController = loginController;
        var user = new InputFormFieldControllerImpl("Username", Validators.notBlank("*Required"));
        var pass = new InputFormFieldControllerImpl("Password", Validators.notBlank("*Required"));
        var formGroupController = new FormGroupControllerImpl("Login form", List.of(
                user, pass
        ));
        var userFormField = new InputFormField("Username", 0);
        var passwordFormField = new PasswordInputFormField("Password", 1);
        userFormField.setController(user);
        passwordFormField.setController(pass);
        screen.setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        c.gridy = 0;
        var form = new QuickFormUI("Login form", List.of(userFormField, passwordFormField));
        form.setPreferredSize(new Dimension(500, 250));
        form.setMinimumSize(new Dimension(400, 225));
        form.setMaximumSize(new Dimension(600, 300));
        screen.add(form, c);
        c.anchor = GridBagConstraints.LINE_END;
        c.gridy = 1;

        var btn = new JButton("Login");
        btn.setEnabled(false);
        btn.addActionListener(ac->{
            if(loginController.login(user.getValue(), pass.getValue())) {
                navigator.changeTo(MainScreen.class, Map.of());
            }
            else messageDisplayer.displayInformation("Login failed", "Username and password not match");
        });
        formGroupController.subscribeValueChange(()->{
            btn.setEnabled(formGroupController.isValid());
        });
        screen.add(btn, c);
    }

    @Override
    public String getTitle() {
        return "Login to admin";
    }

    @Override
    public void init(Map<String, Object> argument) {
    }
}
