package controller;

import domain.Media;

public interface IAdminItemAdvice {
    void editLog(int id);

    void updatePriceLog(int id);

    boolean canUpdatePrice(int mediaId);

    boolean canEdit(int mediaId);

    boolean canDelete(int count);
}
