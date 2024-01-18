package controller;

import domain.Media;

import java.util.HashMap;

public class AdminItemAdvice implements IAdminItemAdvice {
    private int editCount = 0;
    private final HashMap<Integer, Integer> priceCount = new HashMap<>();

    @Override
    public void editLog(int id) {
        editCount++;
    }
    @Override
    public void updatePriceLog(int id) {
        int last = priceCount.getOrDefault(id, 0);
        priceCount.put(id, last + 1);
    }

    @Override
    public boolean canUpdatePrice(int id) {
        return priceCount.getOrDefault(id, 0) < 2;
    }
    @Override
    public boolean canEdit(int id) {
        return editCount < 30;
    }

    @Override
    public boolean canDelete(int count) {
        return count+editCount<30;
    }
}
