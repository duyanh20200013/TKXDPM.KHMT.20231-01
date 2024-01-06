package test;

import controller.PlaceOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateShippingFeeTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() throws Exception {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "70000, 2, Hà Nội, 0, 22000",
            "125000, 1.5, Hà Nam, 0, 0",
            "230000, 2.5, Hồ Chí Minh, 3, 0",
            "145000, 3.5, Hà Nội, 0, 0",
            "25000, 1, Hà Nội, 1, 32000",
    })

    public void test(int totalPrice, double weight, String province, int quantitySupportRush, int expected) {
        int shippingFee = placeOrderController.calculateShippingFee(totalPrice, weight, province, quantitySupportRush);
        assertEquals(expected, shippingFee);
    }
}
