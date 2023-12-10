package controller.impl;

import controller.IPaginatorController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PaginatorController implements IPaginatorController {
    private Supplier<Integer> countService;
    private final List<PageControlCallback> callbacks = new ArrayList<>();
    private int pageSize = 5;
    private int start;
    private int end;
    private int tt;

    PaginatorController(Supplier<Integer> integerSupplier) {
        setItemCounter(integerSupplier);
    }

    void setItemCounter(Supplier<Integer> itemCountService) {
        if(itemCountService == null) throw new IllegalArgumentException("Not null");
        tt = itemCountService.get();
        countService = itemCountService;
        firstPage();
    }

    @Override
    public void addCallback(PageControlCallback callback) {
        callbacks.add(callback);
        invokeAllCallback();
    }

    @Override
    public void setPageSize(int size) {
        pageSize = size;
        start = 0;
        end = pageSize;
        firstPage();
    }

    @Override
    public void invokeAllCallback() {
        callbacks.forEach(callback -> callback.render(start, end, tt));
    }

    @Override
    public void nextPage() {
        tt = countService.get();
        if(end < tt) {
            start += pageSize;
            end += pageSize;
            end = Math.min(end, tt);
        }
        invokeAllCallback();
    }

    @Override
    public void prevPage() {
        if(start > 0) {
            start -=  pageSize;
            start = Math.max(0, start);
            end -= pageSize;
        }
        invokeAllCallback();
    }

    @Override
    public void lastPage() {
        tt = countService.get();
        while(end < tt) {
            start += pageSize;
            end += pageSize;
            end = Math.min(end, tt);
        }
        invokeAllCallback();
    }

    @Override
    public void firstPage() {
        start = 0;
        end = pageSize;
        invokeAllCallback();
    }
}
