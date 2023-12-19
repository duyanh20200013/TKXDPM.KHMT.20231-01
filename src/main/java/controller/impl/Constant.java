package controller.impl;

import controller.IEtcController;

public class Constant implements IEtcController {
    @Override
    public String getCurrency() {
        return "VND";
    }

    @Override
    public int getDefaultPageSize() {
        return 5;
    }
}
