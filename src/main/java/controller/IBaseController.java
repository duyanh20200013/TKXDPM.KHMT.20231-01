package controller;

import java.util.Date;

public interface IBaseController {
    String getCurrency();

    String formatMoney(long amount);
    String formatDate(Date date);
}
