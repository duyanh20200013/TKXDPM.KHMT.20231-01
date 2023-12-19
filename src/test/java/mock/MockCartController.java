package mock;

import controller.ICartController;
import controller.ICartItemController;
import controller.IPaginatorController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockCartController implements ICartController {
    @Override
    public String getTotalMoney() {
        return "";
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemTypeCount() {
        return 0;
    }

    @Override
    public String getSavedDate() {
        return "";
    }

    @Override
    public IPaginatorController getPaginatorController() {
        return new MockPaginatorController();
    }

    @Override
    public List<ICartItemController> getPage(int u, int v) {
        ArrayList<ICartItemController> arrayList = new ArrayList<>();
        for(int i=u;i<v;++i)
            arrayList.add(new MockCartITemController());
        return arrayList;
    }
}
