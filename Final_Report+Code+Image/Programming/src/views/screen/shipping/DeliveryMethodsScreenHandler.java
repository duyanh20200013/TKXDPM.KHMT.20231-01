package views.screen.shipping;

import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import common.exception.InvalidDeliveryInfoException;
import views.screen.popup.PopupScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DeliveryMethodsScreenHandler extends BaseScreenHandler implements Initializable {

    private Order order;

    @FXML
    private RadioButton placeRushOrderValue;

    @FXML
    private RadioButton placeOrderValue;

    @FXML
    private TextField deliveryInstruction;

    @FXML
    private DatePicker deliveryTime;

    @FXML
    private Label errorProvince;

    @FXML
    private Label errorSupportRush;

    @FXML
    private Button updateDeliveryMethodInfoButton;

    public DeliveryMethodsScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
        super(stage, screenPath);
        this.order = order;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.placeOrderValue.setSelected(true);
        this.errorProvince.setVisible(false);
        this.errorSupportRush.setVisible(false);
        this.deliveryInstruction.setDisable(true);
        this.deliveryTime.setDisable(true);
    }

    /**
     * @param event
     * @throws IOException
     */
    @FXML
    private void updateDeliveryMethodInfo(MouseEvent event) throws IOException,InterruptedException, SQLException {
        String deliveryInstructionString = new String(deliveryInstruction.getText());
        String deliveryDateString = new String();
        if (deliveryTime.getValue() != null) {
            deliveryDateString = new String(deliveryTime.getValue().toString());
        }else{
            deliveryDateString = null;
        }

        HashMap<String, String> deliveryData = new HashMap<String, String>();
        deliveryData.put("deliveryInstruction", deliveryInstructionString);
        deliveryData.put("deliveryDate", deliveryDateString);

        HashMap<String, String> deliveryInfo = this.order.getDeliveryInfo();
        String province = new String(deliveryInfo.get("province"));
        int typeDelivery;
        if (placeRushOrderValue.isSelected() && province.equals("Hà Nội")) {
            typeDelivery = utils.Configs.PLACE_RUSH_ORDER;
        } else {
            typeDelivery = utils.Configs.PALCE_ORDER;
        }
       try{
           PlaceRushOrderController.validatePlaceRushOrderData(deliveryData, typeDelivery);
       }catch (InvalidDeliveryInfoException e) {
//                String message = "Not enough media";
//                LOGGER.severe(message);
            PopupScreen.error(e.getMessage());
            return;
            // throw new InvalidDeliveryInfoException(e.getMessage());
        }
       // Cal Sipping Fee
        int numberFast = 0;
        if(typeDelivery == utils.Configs.PLACE_RUSH_ORDER){
            numberFast = order.getNumberFast();
        }
        int shippingFees = getBController().calculateShippingFee(order.getAmount()*1000,order.getWeight(),province,numberFast);
        order.setShippingFees(shippingFees/1000);
        // // create invoice screen
        Invoice invoice = getBController().createInvoice(order);
        BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
        InvoiceScreenHandler.setPreviousScreen(this);
        InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
        InvoiceScreenHandler.setScreenTitle("Invoice Screen");
        InvoiceScreenHandler.setBController(getBController());
        InvoiceScreenHandler.show();
    }


    /**
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleBack(MouseEvent event) throws IOException {
        // Back to previous screen
        BaseScreenHandler ShippingScreenHandler = new ShippingScreenHandler(this.stage, Configs.SHIPPING_SCREEN_PATH,
                this.order);
        ShippingScreenHandler.setPreviousScreen(this);
        ShippingScreenHandler.setHomeScreenHandler(homeScreenHandler);
        ShippingScreenHandler.setScreenTitle("Shipping Screen");
        ShippingScreenHandler.setBController(getBController());
        ShippingScreenHandler.show();
    }


    /**
     * @param event
     */
    @FXML
    private void handleDeliveryType(ActionEvent event) {
        if (placeOrderValue.isSelected()) {
            deliveryInstruction.setDisable(true);
            deliveryTime.setDisable(true);
        } else if (placeRushOrderValue.isSelected()) {
            deliveryInstruction.setDisable(false);
            deliveryTime.setDisable(false);
        }
        handleProvinceError(event);
    }


    /**
     * @param event
     */
    @FXML
    private void handleProvinceError(ActionEvent event) {
        HashMap<String, String> deliveryInfo = this.order.getDeliveryInfo();
        String province = new String(deliveryInfo.get("province"));

        errorProvince.setVisible(false);
        deliveryInstruction.setDisable(true);
        deliveryTime.setDisable(true);
        updateDeliveryMethodInfoButton.setDisable(false);

        if (!province.equals("Hà Nội")) {
            if (placeRushOrderValue.isSelected()) {
                errorProvince.setVisible(true);
                deliveryInstruction.setDisable(true);
                deliveryTime.setDisable(true);
                updateDeliveryMethodInfoButton.setDisable(true);
            } else {
                updateDeliveryMethodInfoButton.setDisable(false);
                deliveryInstruction.setDisable(true);
                deliveryTime.setDisable(true);
            }
        }
        else {
            if(order.getNumberFast() >0){
                if (placeRushOrderValue.isSelected()) {
                    deliveryInstruction.setDisable(false);
                    deliveryTime.setDisable(false);
                    updateDeliveryMethodInfoButton.setDisable(false);
                } else {
                    updateDeliveryMethodInfoButton.setDisable(false);
                    deliveryInstruction.setDisable(true);
                    deliveryTime.setDisable(true);
                }
            }else{
                if (placeRushOrderValue.isSelected()) {
                    errorSupportRush.setVisible(true);
                    deliveryInstruction.setDisable(true);
                    deliveryTime.setDisable(true);
                    updateDeliveryMethodInfoButton.setDisable(true);
                } else {
                    updateDeliveryMethodInfoButton.setDisable(false);
                    deliveryInstruction.setDisable(true);
                    deliveryTime.setDisable(true);
                    errorSupportRush.setVisible(false);
                }
            }

        }
    }

    /**
     * @return PlaceOrderController
     */
    public PlaceOrderController getBController() {
        return (PlaceOrderController) super.getBController();
    }
}
