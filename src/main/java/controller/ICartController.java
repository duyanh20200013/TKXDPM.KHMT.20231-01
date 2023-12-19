package controller;

import java.util.Date;
import java.util.List;

public interface ICartController {
    String getTotalMoney();

    int getItemCount();

    int getItemTypeCount();

    String getSavedDate();
    IPaginatorController getPaginatorController();
    List<ICartItemController> getPage(int u, int v);
    default void reload() {};
}
