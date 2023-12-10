package controller.impl;

import controller.ICartItemController;
import dmui.content.BasePanel;
import domain.CartItemDomain;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class CartItemController extends BaseController implements ICartItemController {
    private final CartItemDomain cartItemDomain;
    private final CartController parrent;
    CartItemController(CartItemDomain cartItemDomain, CartController parrent) {
        this.cartItemDomain = cartItemDomain;
        this.parrent = parrent;
    }
    @Override
    public void remove() {
    }

    @Override
    public boolean checkRemain() {
        return cartItemDomain.hasEnough();
    }

    @Override
    public long getTotalPrice() {
        return cartItemDomain.getPrice();
    }

    @Override
    public long getItemPrice() {
        return cartItemDomain.getEachItemPrice();
    }

    @Override
    public void plus() {
        cartItemDomain.inc();
    }

    @Override
    public void minus() {
        cartItemDomain.dec();
    }

    @Override
    public void setItemCount(int count) {
        cartItemDomain.setCount(count);
    }

    @Override
    public String getTitle() {
        return cartItemDomain.getTitle();
    }

    @Override
    public String getType() {
        return cartItemDomain.getType();
    }

    @Override
    public int getCount() {
        return cartItemDomain.getCount();
    }

    @Override
    public Image getImage() throws IOException {
        return ImageIO.read(cartItemDomain.getImageData());
    }
}
