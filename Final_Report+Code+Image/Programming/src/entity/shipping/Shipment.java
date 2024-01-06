package entity.shipping;

import jdk.jshell.spi.ExecutionControl;

public class Shipment {

    private int shipType;

    private String deliveryInstruction;

    private String deliveryTime;

    //constructor operation
    public Shipment(int shipType, String deliveryInstruction, String deliveryTime) {
        super();
        if (shipType == utils.Configs.PLACE_RUSH_ORDER) {
            this.deliveryInstruction = deliveryInstruction;
            this.deliveryTime = deliveryTime;
        }
    }

    public Shipment(int shipType) {
        super();
    }


    /**
     * @return String
     */
    //getter setter method
    public String getDeliveryInstruction() {
        return this.deliveryInstruction;
    }

    public void validateDeliveryInfo() {
        // TODO: implement later on
    }


    /**
     * @return Shipment
     */
    public Shipment createNewShipment() throws ExecutionControl.NotImplementedException {
        // TODO: implement later on
        throw new ExecutionControl.NotImplementedException("abc");
    }
}
