package form_controller_impl;

import java.util.List;
import java.util.function.Function;

public class Validators {
    public static Function<String, String> ofAll(Function<String, String> ...validators) {
        return s->{
            for(var v : validators) {
                var res = v.apply(s);
                if(res != null) return res;
            }
            return null;
        };
    }
    public static Function<String, String> notBlank(String defaultMessage) {
        return s->{
            if(s == null || s.isBlank()) return defaultMessage;
            return null;
        };
    }

    public static Function<String, String> notEmpty(String defaultMessage) {
         return s->{
            if(s == null || s.isEmpty()) return defaultMessage;
            return null;
        };
    }

    public static Function<String, String> positiveNumber(String defaultMessage) {
        return s->{
            if(s==null) return defaultMessage;
            try {
                var p = Long.parseLong(s);
                if(p>=0) return null;
                else return defaultMessage;
            } catch (NumberFormatException ex) {
                return defaultMessage;
            }
        };
    }
}
