package service;

import java.io.InputStream;

public interface ICatalog {
    boolean checkItemHasEnoughItem(long itemId, int count);

    InputStream getItemImage(long itemId);
}
