package test;

import controller.PlaceOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidateDeliveryInfoTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() throws Exception {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "29, 2904200202, Hà Nội, 'Truong Dinh, Hai Bà Trưng',, false",
            "Dinh Duy Canh@, 06431384351, Hà Nội, 'Truong Dinh, Hai Bà Trưng',, false",
            "Dinh Duy Anh, jhsgghsd, Hà Nội, 'Truong Dinh, Hai Bà Trưng',, false",
            "Dinh Duy Anh, 757675325, Hà Nội, 'Truong Dinh, Hai Bà Trưng',, false",
            "Dinh Duy Anh, 0757675325534, Hà Nội, 'Truong Dinh, Hai Bà Trưng',, false",
            "Dinh Duy Anh, 0329850375,, 'Truong Dinh, Hai Bà Trưng',, false",
            "Dinh Duy Anh, 0329850375, Hà Nội,,, false",
            "Dinh Duy Anh, 0329850375, Hà Nội, 'Thanh Nhàn, Hai Bà Trưng',, true",
            "Nguyen Van A, 0353785838, Hải Phòng, Nhà 123 xã B,, true",
    })

    public void test(String name, String phone, String province, String address, String instruction, boolean expected) {
        boolean isValid = placeOrderController.validateDeliveryInfoTest(name, phone, province, address, instruction);
        assertEquals(expected, isValid);
    }
}
