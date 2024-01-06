package controller;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import common.exception.InvalidDeliveryInfoException;

/**
 * This class controls the flow of place order usecase in our AIMS project
 *
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController {

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder
     * button
     *
     * @throws SQLException
     */
    public void placeOrder() throws SQLException {
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     *
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException {
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                    cartMedia.getQuantity(),
                    cartMedia.getPrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     *
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     *
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException {
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }

    /**
     * The method validates the info
     *
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {
        if(!validateName(info.get("name"))) throw new InvalidDeliveryInfoException("Name is not Correct");
        if(!validatePhoneNumber(info.get("phone"))) throw new InvalidDeliveryInfoException("Phone is not Correct");
        if(info.get("province")==null) throw new InvalidDeliveryInfoException("City is Null");
        if(!validateAddress(info.get("address"))) throw new InvalidDeliveryInfoException("Adress is not Correct");
    }

    public boolean validateDeliveryInfoTest(String name, String phone, String province, String address, String instruction) {
        if (!validateName(name)) return false;
        if (!validatePhoneNumber(phone)) return false;
        if (province == null) return false;
        return validateAddress(address);
    }

    /**
     * @param phoneNumber
     * @return boolean
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        // check the phoneNumber has 10 digits
        if (phoneNumber.length() != 10)
            return false;
        if (Character.compare(phoneNumber.charAt(0), '0') != 0)
            return false;
        // check the phoneNumber contains only number
        try {
            Integer.parseInt(phoneNumber);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }


    /**
     * @param name
     * @return boolean
     */
    public boolean validateName(String name) {
        // Check name is not null
        if (name == null)
            return false;
        // Check if contain leter space only
        if (name.trim().length() == 0)
            return false;
        // Check if contain only leter and space
        if (name.matches("^[a-zA-Z ]*$") == false)
            return false;
        return true;
    }


    /**
     * @param address
     * @return boolean
     */
    public boolean validateAddress(String address) {
        // Check address is not null
        if (address == null)
            return false;
        return true;
    }

    /**
     * This method calculates the shipping fees of order
     *
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(int totalPrice,double weight, String province,int quantitySupportRush) {
        int fees =0;
        if(totalPrice <= 100000) {
            double giaKhoiDiem = 30000;
            double weightKhoiDiem = 0.5;
            if(province.equals("Hà Nội") || province.equals("Hồ Chí Minh")){
                giaKhoiDiem = 22000;
                weightKhoiDiem = 3;
            }

            if(weight <= weightKhoiDiem) fees = (int) giaKhoiDiem;
            if(weight > weightKhoiDiem) {
                fees = (int) (giaKhoiDiem+ (weight - weightKhoiDiem)/0.5*2500);
            }

            fees = fees + quantitySupportRush*10000;
        }
        return fees;
    }

    /**
     * This method get product available place rush order media
     *
     * @param order
     * @return media
     * @throws SQLException
     */
    public Media getProductAvailablePlaceRush(Order order) throws SQLException {
        Media media = new Media();
        HashMap<String, String> deliveryInfo = order.getDeliveryInfo();
        validateAddressPlaceRushOrder(deliveryInfo.get("province"), deliveryInfo.get("address"));
        for (Object object : order.getlstOrderMedia()) {
            // CartMedia cartMedia = (CartMedia) object;
            validateMediaPlaceRushorder();
        }
        return media;
    }


    /**
     * @param province
     * @param address
     * @return boolean
     */
    public boolean validateAddressPlaceRushOrder(String province, String address) {
        if (!validateAddress(address))
            return false;
        if (!province.equals("Hà Nội"))
            return false;
        return true;
    }


    /**
     * @return boolean
     */
    public boolean validateMediaPlaceRushorder() {
        if (Media.getIsSupportedPlaceRushOrder())
            return true;
        return false;
    }
}
