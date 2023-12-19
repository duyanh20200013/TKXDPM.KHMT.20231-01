package controller.impl;

import controller.IPaginatorController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BoundedPaginator implements IPaginatorController {
    private final Supplier<Integer> boundService;
    private final List<PageControlCallback> callbacks = new ArrayList<>();
    private int pageSize;
    private int start;
    private int end;
    private int tt;

    public BoundedPaginator(Supplier<Integer> boundService, int pageSize) {
        this.boundService = boundService;
        tt = boundService.get();
        setPageSize(pageSize);
    }

    @Override
    public void addCallback(PageControlCallback callback) {
        callbacks.add(callback);
        callback.render(start, end, tt);
    }

    @Override
    public void setPageSize(int size) {
        if(size <= 0) throw new IllegalArgumentException("Must be positive");
        pageSize = size;
        firstPage();
    }

    @Override
    public void invokeAllCallback() {
        callbacks.forEach(callback -> callback.render(start, end, tt));
    }
    @Override
    public void nextPage() {
        tt = boundService.get();
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
        tt = boundService.get();
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
