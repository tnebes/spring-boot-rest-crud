package hr.tnebes.crud.controllers.impl;


import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.FakerService;
import hr.tnebes.crud.services.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static hr.tnebes.crud.models.product.availability.ProductAvailability.AVAILABLE;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductReadControllerImpl.class)
class ProductReadControllerImplTest {

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
        this.mockedProductList = this.fakerService.generateFakeTestProductList();
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
                .andExpect(jsonPath("$[0].description").value(""))
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
}