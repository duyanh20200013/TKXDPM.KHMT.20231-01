package domain;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface ICartItemDomain {
    int getItemId();
    boolean hasEnough();
    void setCount(int count);
    long getPrice();
    long getEachItemPrice();
    String getTitle();
    String getType();
    int getCount();
    InputStream getImageData() throws IOException;
}
