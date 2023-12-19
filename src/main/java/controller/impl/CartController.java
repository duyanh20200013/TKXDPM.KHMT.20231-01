package controller.impl;

import controller.ICartController;
import controller.ICartItemController;
import controller.IEtcController;
import controller.IPaginatorController;
import domain.ICartDomain;

import java.util.Date;
import java.util.List;

public class CartController extends BaseController implements ICartController {
    final ICartDomain cart;
    final BoundedPaginator paginator;

    public CartController(ICartDomain cart, IEtcController etc) {
        super();
        this.cart = cart;
        paginator = new BoundedPaginator(this::getItemTypeCount, etc.getDefaultPageSize());
    }

    @Override
    public String getTotalMoney() {
        return formatMoney(cart.getRawPrice());
    }

    @Override
    public int getItemCount() {
        return cart.countItem();
    }

    @Override
    public int getItemTypeCount() {
        return cart.countItemType();
    }

    @Override
    public String getSavedDate() {
        return formatDate(cart.getSavedDate());
    }

    @Override
    public IPaginatorController getPaginatorController() {
        return paginator;
    }

    @Override
    public List<ICartItemController> getPage(int u, int v) {
        return cart.getPage(u, v).stream().map(i->(ICartItemController)new CartItemController(i, this)).toList();
    }
}
