package hr.tnebes.crud;

import hr.tnebes.crud.dtos.ProductDto;
import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.models.product.availability.ProductAvailability;

import java.math.BigDecimal;

import static hr.tnebes.crud.models.product.availability.ProductAvailability.AVAILABLE;

public class TestConstants {

    public static final ProductDto EMPTY_PRODUCT_DTO = ProductDto.builder().build();
    public static final ProductModel EMPTY_PRODUCT_MODEL = new ProductModel();
    public static final ProductDto INVALID_PRODUCT_DTO = ProductDto.builder()
            .code("")
            .name("")
            .description("")
            .availability(AVAILABLE)
            .priceEur(BigDecimal.valueOf(-1))
            .priceHrk(BigDecimal.valueOf(-1.5))
            .quantity(-100)
            .build();
    public static final ProductModel INVALID_PRODUCT_MODEL = new ProductModel( 1000L, "123456", "invalid product 1", new BigDecimal("-1.00"), new BigDecimal("-0.50"), "invalid product 1", AVAILABLE, -1);
    public static final ProductDto VALID_PRODUCT_DTO = new ProductDto( 0l, "1234567800", "Test product 1", new BigDecimal("1.00"), new BigDecimal("0.50"), "", AVAILABLE, 1);
    public static final ProductModel VALID_PRODUCT_MODEL = new ProductModel( 1000L, "1234567800", "Test product 1", new BigDecimal("1.00"), new BigDecimal("0.50"), "", AVAILABLE, 1);

    private TestConstants() {
    }

}