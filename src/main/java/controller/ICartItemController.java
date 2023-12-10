package controller;

import java.awt.*;
import java.io.IOException;

public interface ICartItemController extends IBaseController {
    void remove();

    boolean checkRemain();

    long getTotalPrice();

    long getItemPrice();

    void plus();

    void minus();

    void setItemCount(int count);

    String getTitle();

    String getType();

    int getCount();

    Image getImage() throws IOException;
}
