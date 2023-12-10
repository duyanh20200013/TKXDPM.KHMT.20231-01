package controller.impl;

import controller.IBaseController;

import java.util.Date;

public class BaseController implements IBaseController {
    @Override
    public String getCurrency() {
        return "VND";
    }

    @Override
    public String formatMoney(long amount) {
        long front = amount / 100;
        long back = amount % 100;
        return front + "." + "%02d".formatted(back);
    }

    @Override
    public String formatDate(Date date) {
        //TODO:
        return "";
    }
}
