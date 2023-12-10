package controller;

import java.util.List;
import java.util.function.Supplier;

public interface IPaginatorController {
    interface PageControlCallback {
        void render(int start, int end, int count);
    }
    void addCallback(PageControlCallback callback);
    void setPageSize(int size);
    void invokeAllCallback();
    void nextPage();
    void prevPage();
    void lastPage();
    void firstPage();
}
