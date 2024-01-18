package ui.form.base;

import utils.Subscription;
import java.util.Optional;

public interface FormController {
    String getName();
    Optional<String> getError();
    Subscription subscribeValueChange(Runnable runnable);
}
