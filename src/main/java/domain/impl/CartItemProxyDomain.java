package domain.impl;

import domain.ICartItemDomain;
import domain.IItemDomain;
import repo.IItemRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CartItemProxyDomain implements ICartItemDomain {
    private final IItemDomain proxy;
    private int count;

    CartItemProxyDomain(IItemDomain proxy, int count) {
        this.proxy = proxy;
        this.count = count;
    }

    @Override
    public int getItemId() {
        return proxy.getItemId();
    }

    @Override
    public boolean hasEnough() {
        return proxy.hasEnough(count);
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public long getPrice() {
        return proxy.getPrice() * count;
    }

    @Override
    public long getEachItemPrice() {
        return proxy.getPrice();
    }

    @Override
    public String getTitle() {
        return proxy.getTitle();
    }

    @Override
    public String getType() {
        return proxy.getType();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public InputStream getImageData() throws IOException {
        return proxy.getImage();
    }
}
