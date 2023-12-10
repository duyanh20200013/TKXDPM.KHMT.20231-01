package controller.impl;

import controller.ICartController;
import controller.ICartItemController;
import controller.IPaginatorController;
import domain.CartDomain;
import service.ICart;

import java.util.Date;
import java.util.List;

public class CartController extends BaseController implements ICartController {
    private final CartDomain cart;

    public CartController(CartDomain cart) {
        this.cart = cart;
    }

    @Override
    public long getTotalMoney() {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemTypeCount() {
        return 0;
    }

    @Override
    public Date getSavedDate() {
        //TODO:
        return new Date();
    }

    @Override
    public IPaginatorController getPaginatorController() {
        //TODO:
        return null;
    }

    @Override
    public List<ICartItemController> getPage(int u, int v) {
        return cart.getPage(u, v).stream().map(i->(ICartItemController)new CartItemController(i, this)).toList();
    }
}
