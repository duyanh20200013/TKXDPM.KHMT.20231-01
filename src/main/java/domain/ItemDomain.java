package domain;

import lombok.Getter;
import service.ICatalog;

import java.io.InputStream;

public class ItemDomain {
    protected final ICatalog iCatalog;
    @Getter
    private long price;
    protected long itemId;
    @Getter
    private String type;
    @Getter
    private String title;

    ItemDomain(ICatalog catalog, long itemId) {
        this.iCatalog = catalog;
        this.itemId = itemId;
    }

    public InputStream getImageData() {
        return iCatalog.getItemImage(itemId);
    }

}
