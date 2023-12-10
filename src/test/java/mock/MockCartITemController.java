package mock;

import controller.ICartItemController;

import java.awt.*;
import java.io.IOException;
import java.util.Date;

public class MockCartITemController implements ICartItemController {
    @Override
    public String getCurrency() {
        return "VND";
    }
    int count;

    @Override
    public String formatMoney(long amount) {
        return Long.toString(amount);
    }

    @Override
    public String formatDate(Date date) {
        return "";
    }

    @Override
    public void remove() {
    }

    @Override
    public boolean checkRemain() {
        return count < 10;
    }

    @Override
    public long getTotalPrice() {
        return 0;
    }

    @Override
    public long getItemPrice() {
        return 0;
    }

    @Override
    public void plus() {
        ++count;
    }

    @Override
    public void minus() {
        --count;
    }

    @Override
    public void setItemCount(int count) {
        this.count = count;
    }

    @Override
    public String getTitle() {
        return "Title";
    }

    @Override
    public String getType() {
        return "Type";
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Image getImage() throws IOException {
        return null;
    }
}
