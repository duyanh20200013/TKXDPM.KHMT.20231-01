package controller;

import entity.shipping.Shipment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.Logger;
import common.exception.InvalidDeliveryInfoException;

/**
 * This class controls the flow of place rush order usecase in our AIMS project
 *
 * @author giangleee
 */
public class PlaceRushOrderController extends BaseController {
    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());


    /**
     * @param deliveryData
     * @param typeDelivery
     */
    public static void validatePlaceRushOrderData(HashMap<String, String> deliveryData, int typeDelivery) throws InterruptedException, IOException {
        if (typeDelivery == utils.Configs.PLACE_RUSH_ORDER) {
            if(!validateDeliveryInstructionPlaceRushOrder(deliveryData.get("deliveryInstruction"))) throw new InvalidDeliveryInfoException("Chỉ dẫn giao hàng không hợp lệ");
            if(!validateDeliveryDatePlaceRushOrder(deliveryData.get("deliveryDate"))) throw new InvalidDeliveryInfoException("Thời gian giao hàng không hợp lệ");
            Shipment shipment = new Shipment(typeDelivery, deliveryData.get("deliveryInstruction"), deliveryData.get("deliveryDate"));
        }
    }

    public boolean validatePlaceRushOrderTest(String deliveryTime, String deliveryInstruction) {
        if(!validateDeliveryDatePlaceRushOrder(deliveryTime)) return false;
        if(!validateDeliveryInstructionPlaceRushOrder(deliveryInstruction)) return false;
        return true;
    }

    public static boolean validateDeliveryInstructionPlaceRushOrder(String deliveryInstruction) {
        if(deliveryInstruction == null) return false;
        if (deliveryInstruction.trim().length() == 0)
            return false;
        return true;
    }

    public static boolean validateDeliveryDatePlaceRushOrder(String deliveryDate) {
        if(deliveryDate == null) return false;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(deliveryDate, dateTimeFormatter);
        if(date.compareTo(LocalDate.now()) > 0) return true;
        return false;
    }
}
