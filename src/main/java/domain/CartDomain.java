package domain;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public class CartDomain {
    private Date lastUpdate;
    private final SortedSet<CartItemDomain> items;

    public CartDomain(Date lastUpdate, SortedSet<CartItemDomain> items) {
        this.lastUpdate = lastUpdate;
        this.items = items;
    }
    public List<CartItemDomain> getPage(int u, int v) {
        return items.stream().skip(u).limit(v).toList();
    }

    public void addItem(CartItemDomain cartItemDomain) {
        lastUpdate = new Date();
        items.add(cartItemDomain);
    }

    public void removeItem(CartItemDomain cartItemDomain) {
        lastUpdate = new Date();
        items.remove(cartItemDomain);
    }

    public long getRawPrice() {
        long d = 0;
        for(var i : items)
            d+=i.getPrice();
        return d;
    }
}

