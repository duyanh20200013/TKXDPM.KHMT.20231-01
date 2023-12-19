package controller.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {
    //Prepare for multithread enviroment
    private final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(()-> new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
    public String formatMoney(long amount) {
        long front = amount / 100;
        long back = amount % 100;
        return front + "." + "%02d".formatted(back);
    }

    public String formatDate(Date date) {
        return threadLocal.get().format(date);
    }
}
