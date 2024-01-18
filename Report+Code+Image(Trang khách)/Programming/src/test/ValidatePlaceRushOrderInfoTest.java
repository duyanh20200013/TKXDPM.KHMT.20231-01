package test;

import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatePlaceRushOrderInfoTest {
    private PlaceRushOrderController placeRushOrderController;

    @BeforeEach
    void setUp() throws Exception {
        placeRushOrderController = new PlaceRushOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            ", Giao trong khoảng 8-9h sáng, false",
            "2022-11-06, Giao trong khoảng 8-9h sáng, false",
            "2023-12-11,, false",
            "2023-12-11, Giao trong khoảng 8-9h sáng, true",
            "2023-12-12, Giao sau 12h trưa, true",

    })

    public void test(String deliveryTime, String deliveryInstruction, boolean expected) {
        boolean isValid = placeRushOrderController.validatePlaceRushOrderTest(deliveryTime, deliveryInstruction);
        assertEquals(expected, isValid);
    }
}
