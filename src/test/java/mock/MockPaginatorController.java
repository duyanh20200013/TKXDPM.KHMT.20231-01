package mock;

import controller.IPaginatorController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MockPaginatorController implements IPaginatorController {
    List<PageControlCallback> pageControlCallbacks = new ArrayList<>();
    @Override
    public void addCallback(PageControlCallback callback) {
        pageControlCallbacks.add(callback);
    }

    @Override
    public void setPageSize(int size) {
    }

    @Override
    public void invokeAllCallback() {
        pageControlCallbacks.forEach(i->i.render(0, 10, 12));
    }

    @Override
    public void nextPage() {
    }

    @Override
    public void prevPage() {
    }

    @Override
    public void lastPage() {
    }

    @Override
    public void firstPage() {
    }
}
