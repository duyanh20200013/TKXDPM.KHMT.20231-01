package domain;

import lombok.Getter;
import service.ICatalog;

import java.util.Date;

public class CartItemDomain extends ItemDomain {
    @Getter
    private int count;
    private Date lastUpdate;
    private final Date addDate;

    CartItemDomain(ICatalog catalog, long itemId, int count, Date date) {
        super(catalog, itemId);
        this.count = count;
        this.addDate = date;
    }

    public boolean hasEnough() {
        return iCatalog.checkItemHasEnoughItem(itemId, count);
    }

    public void inc() {
        lastUpdate = new Date();
        count++;
    }
    public void dec() {
        lastUpdate = new Date();
        count--;
    }
    public void setCount(int count) {
        this.count = count;
        lastUpdate = new Date();
    }
    public long getPrice() {
        return super.getPrice() * count;
    }

    public long getEachItemPrice() {
        return super.getPrice();
    }
}
