package controller;

import java.util.Date;
import java.util.List;

public interface ICartController extends IBaseController {
    long getTotalMoney();

    int getItemCount();

    int getItemTypeCount();

    Date getSavedDate();
    IPaginatorController getPaginatorController();
    List<ICartItemController> getPage(int u, int v);
}
