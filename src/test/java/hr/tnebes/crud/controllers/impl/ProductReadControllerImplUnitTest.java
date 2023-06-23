package hr.tnebes.crud.controllers.impl;


import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.models.product.availability.ProductAvailability;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.FakerService;
import hr.tnebes.crud.services.ProductService;

import hr.tnebes.crud.utils.LocaleUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static hr.tnebes.crud.models.product.availability.ProductAvailability.AVAILABLE;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductReadControllerImpl.class)
class ProductReadControllerImplUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FakerService fakerService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductService productService;

    List<ProductModel> mockedProductList;

    @BeforeEach
    void setUpBeforeEach() {
        this.mockedProductList = this.fakerService.generatePredeterminedFakeTestProductList();
        Mockito.when(this.productRepository.findAll()).thenReturn(this.mockedProductList);
    }

    @DisplayName("GIVEN a controller with 9 products in the database " +
            "WHEN the getAllProducts() method is called " +
            "THEN the controller should return a list of 9 products")
    @Test
    void testGetAllProductsReturnsNineProducts() throws Exception {
        this.mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(9)))
                .andExpect(jsonPath("$[0].code").value(1234567800L))
                .andExpect(jsonPath("$[0].name").value("Test product 1"))
                .andExpect(jsonPath("$[0].priceHrk").value(1.00))
                .andExpect(jsonPath("$[0].priceEur").value(0.50))
                .andExpect(jsonPath("$[0].description").value("Test description"))
                .andExpect(jsonPath("$[0].availability").value(AVAILABLE.toString()))
                .andExpect(jsonPath("$[0].quantity").value(1));
    }

    @DisplayName("GIVEN the controller with 9 products in the database " +
            "WHEN the getProductsById() method is called with a valid id " +
            "THEN the controller should return a list of 1 product")
    @Test
    void testGetProductsByIdReturnsProduct() throws Exception {
        Mockito.when(this.productRepository.findAllById(Mockito.anyList())).thenReturn(List.of(this.mockedProductList.get(0)));
        this.mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @DisplayName("GIVEN the controller with 9 products in the database " +
            "WHEN the getProductsById() method is called with a valid id " +
            "THEN the controller should return a list of 2 products")
    @Test
    void testGetProductsByIdReturnsMultipleProducts() throws Exception {
        Mockito.when(this.productRepository.findAllById(Mockito.anyList())).thenReturn(List.of(this.mockedProductList.get(0), this.mockedProductList.get(1)));
        this.mockMvc.perform(get("/api/v1/product/1,2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @DisplayName("GIVEN the controller with 9 products in the database " +
            "WHEN the getProductsById() method is called with an invalid id " +
            "THEN the controller should return a list of 0 products")
    @Test
    void testGetProductsByIdReturnsErrorWhenInvalidId() throws Exception {
        this.mockMvc.perform(get("/api/v1/product/invalid"))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("GIVEN the controller with 9 products in the database " +
            "WHEN the getProductsByCodes method is called with a valid code " +
            "THEN return a valid product")
    @Test
    void testGetProductsByCodeWithValidCodeReturnsValidProduct() throws Exception {
        final long expectedCode = 1234567800L;
        Mockito.when(this.productRepository.findAllByCodes(Mockito.anyList())).thenReturn(List.of(this.mockedProductList.get(0)));
        this.mockMvc.perform(get("/api/v1/product/codes/" + expectedCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code").value(expectedCode));
    }

    @DisplayName("GIVEN the controller with 9 products in the database " +
            "WHEN the getProductsByCodes method is called with an invalid code " +
            "THEN return an empty list")
    @Test
    void testGetProductsByCodeWithInvalidCodeReturnsEmptyList() throws Exception {
        final String expectedCode = "test";
        this.mockMvc.perform(get("/api/v1/product/codes/" + expectedCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GIVEN a valid product name " +
            "WHEN getProductsByName() is called " +
            "THEN return a list of products with that name")
    void testGetProductsByNameReturnsProducts() throws Exception {
        final String expectedName = "Test product 1";
        final List<ProductModel> expectedProducts = List.of(this.mockedProductList.get(0));
        Mockito.when(this.productRepository.findAllByName(expectedName.trim().toLowerCase(LocaleUtil.CURRENT_LOCALE))).thenReturn(expectedProducts);
        this.mockMvc.perform(get("/api/v1/product/name/" + expectedName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(expectedName));
    }

    @Test
    @DisplayName("GIVEN an empty product name " +
            "WHEN getProductsByName() is called " +
            "THEN return an empty list")
    void testGetProductsByNameWithEmptyNameReturnsEmptyList() throws Exception {
        final String expectedName = StringUtils.EMPTY;
        Mockito.when(this.productRepository.findAllByName(expectedName.trim().toLowerCase(LocaleUtil.CURRENT_LOCALE))).thenReturn(Collections.emptyList());
        this.mockMvc.perform(get("/api/v1/product/name/" + expectedName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("GIVEN a description " +
            "WHEN getProductsByDescription is called " +
            "THEN it should return a list of products with matching description")
    @Test
    void testGetProductsByDescriptionReturnsValidProduct() throws Exception {
        final String expectedDescription = "Test description";
        final List<ProductModel> expectedProducts = List.of(this.mockedProductList.get(0));
        Mockito.when(this.productRepository.findAllByDescription(expectedDescription.trim().toLowerCase(LocaleUtil.CURRENT_LOCALE))).thenReturn(expectedProducts);
        this.mockMvc.perform(get("/api/v1/product/description/" + expectedDescription))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description").value(expectedDescription));
    }

    @DisplayName("GIVEN a non-existing description " +
            "WHEN getProductsByDescription is called " +
            "THEN it should return an empty list")
    @Test
    void testGetProductsByDescriptionWithEmptyDescriptionReturnsEmptyList() throws Exception {
        final String expectedDescription = "non existing description";
        Mockito.when(this.productRepository.findAllByDescription(expectedDescription.trim().toLowerCase(LocaleUtil.CURRENT_LOCALE))).thenReturn(Collections.emptyList());
        this.mockMvc.perform(get("/api/v1/product/description/" + expectedDescription))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("GIVEN a null description " +
            "WHEN getProductsByDescription is called " +
            "THEN it should return an empty list")
    @Test
    void testGetProductsByDescriptionWithNullDescriptionReturnsEmptyList() throws Exception {
        final String expectedDescription = null;
        Mockito.when(this.productRepository.findAllByDescription(expectedDescription)).thenReturn(Collections.emptyList());
        this.mockMvc.perform(get("/api/v1/product/description/" + expectedDescription))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("GIVEN a valid availability " +
            "WHEN getProductsByAvailability is called " +
            "THEN it should return a list of products with matching availability")
    @Test
    void testGetProductsByAvailabilityWithValidAvailabilityReturnsValidProduct() throws Exception {
        final ProductAvailability expectedAvailability = AVAILABLE;
        final List<ProductModel> expectedProducts = this.mockedProductList.stream().filter(product -> product.getAvailability() == expectedAvailability).toList();
        Mockito.when(this.productRepository.findAllByAvailability(expectedAvailability)).thenReturn(expectedProducts);
        this.mockMvc.perform(get("/api/v1/product/availability/" + expectedAvailability))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedProducts.size())))
                .andExpect(jsonPath("$[0].availability").value(expectedAvailability.name()));
    }

    @DisplayName("GIVEN an invalid availability " +
            "WHEN getProductsByAvailability is called " +
            "THEN it should return an empty list")
    @Test
    void testGetProductsByAvailabilityWithInvalidAvailabilityReturnsEmptyList() throws Exception {
        final String expectedAvailability = "invalid";
        this.mockMvc.perform(get("/api/v1/product/availability/" + expectedAvailability))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("GIVEN a valid price eur " +
            "WHEN getProductsByPriceEur is called " +
            "THEN it should return a list of products with matching price eur")
    @Test
    void testGetProductsByPriceEurWithValidEurReturnsValidProducts() throws Exception {
        final double expectedPriceEurDouble = 0.50D;
        final BigDecimal expectedPriceEur = BigDecimal.valueOf(expectedPriceEurDouble);
        final List<ProductModel> expectedProducts = this.mockedProductList.stream().filter(product -> product.getPriceEur().compareTo(expectedPriceEur) == 0).toList();
        Mockito.when(this.productRepository.findAllByPriceEur(expectedPriceEur)).thenReturn(expectedProducts);
        this.mockMvc.perform(get("/api/v1/product/priceEur/" + expectedPriceEur))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedProducts.size())))
                .andExpect(jsonPath("$[0].priceEur").value(expectedPriceEur));
    }

    @DisplayName("GIVEN an invalid price eur " +
            "WHEN getProductsByPriceEur is called " +
            "THEN it should return a list of products with matching price eur")
    @Test
    void testGetProductsByPriceEurWithInvalidEurReturnsNoProducts() throws Exception {
        final String expectedPriceEur = "test price";
        this.mockMvc.perform(get("/api/v1/product/priceEur/" + expectedPriceEur))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("GIVEN a valid price hrk " +
            "WHEN getProductsByPriceEur is called " +
            "THEN it should return a list of products with matching price hrk")
    @Test
    void testGetProductsByPriceHrkWithValidEurReturnsValidProducts() throws Exception {
        final double expectedPriceEurDouble = 1.00D;
        final BigDecimal expectedPriceEur = BigDecimal.valueOf(expectedPriceEurDouble);
        final List<ProductModel> expectedProducts = this.mockedProductList.stream().filter(product -> product.getPriceHrk().compareTo(expectedPriceEur) == 0).toList();
        Mockito.when(this.productRepository.findAllByPriceHrk(expectedPriceEur)).thenReturn(expectedProducts);
        this.mockMvc.perform(get("/api/v1/product/priceHrk/" + expectedPriceEur))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedProducts.size())))
                .andExpect(jsonPath("$[0].priceHrk").value(expectedPriceEur));
    }

    @DisplayName("GIVEN an invalid price hrk " +
            "WHEN getProductsByPriceEur is called " +
            "THEN it should return a list of products with matching price hrk")
    @Test
    void testGetProductsByPriceHrkWithInvalidEurReturnsNoProducts() throws Exception {
        final String expectedPriceEur = "test price";
        this.mockMvc.perform(get("/api/v1/product/priceHrk/" + expectedPriceEur))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }


}