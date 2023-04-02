package hr.tnebes.crud.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tnebes.crud.TestConstants;
import hr.tnebes.crud.dtos.ProductDto;
import hr.tnebes.crud.mappers.ProductMapper;
import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.ProductService;
import hr.tnebes.crud.utils.Constants;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductCreateControllerImpl.class)
class ProductCreateControllerImplUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @DisplayName("GIVEN a valid product dto WHEN sent to controller THEN return the product model.")
    @Test
    void testCreateProductReturnsProductModelOnValidDto() throws Exception {
        final ProductDto productDto = TestConstants.VALID_PRODUCT_DTO;
        final ProductModel productModel = TestConstants.VALID_PRODUCT_MODEL;
        Mockito.when(this.productMapper.toModel(productDto)).thenReturn(productModel);
        Mockito.when(this.productRepository.save(productModel)).thenReturn(productModel);
        this.mockMvc.perform(post("/api/v1/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(productDto.getCode()))
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.priceEur", Matchers.equalTo(productDto.getPriceEur().doubleValue())))
                .andExpect(jsonPath("$.priceHrk", Matchers.equalTo(productDto.getPriceHrk().doubleValue())))
                .andExpect(jsonPath("$.description").value(productDto.getDescription()))
                .andExpect(jsonPath("$.availability").value(productDto.getAvailability().toString()));
        Mockito.verify(this.productMapper, times(1)).toModel(productDto);
        Mockito.verify(this.productRepository, times(1)).save(productModel);
    }

    @DisplayName("GIVEN an invalid product dto WHEN sent to controller THEN return an error message.")
    @Test
    void testCreateProductReturnsExceptionOnEmptyProductDto() throws Exception {
        final ProductDto productDto = TestConstants.EMPTY_PRODUCT_DTO;
        this.mockMvc.perform(post("/api/v1/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasItem(containsString("priceEur must not be null"))))
                .andExpect(jsonPath("$", hasItem(containsString("priceHrk must not be null"))))
                .andExpect(jsonPath("$", hasItem(containsString("availability must not be null"))))
                .andExpect(jsonPath("$", hasItem(containsString("code must not be blank"))))
                .andExpect(jsonPath("$", hasItem(containsString("name must not be blank"))))
                .andExpect(jsonPath("$", hasItem(containsString("quantity must not be null"))));
        Mockito.verify(this.productMapper, never()).toModel(productDto);
        Mockito.verify(this.productRepository, times(Constants.FAKE_DATA_COUNT)).save(Mockito.any(ProductModel.class));
    }

    @DisplayName("GIVEN an invalid product dto WHEN sent to controller THEN return an error message.")
    @Test
    void testCreateProductReturnsExceptionOnInvalidProductDto() throws Exception {
        final ProductDto productDto = TestConstants.INVALID_PRODUCT_DTO;
        this.mockMvc.perform(post("/api/v1/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasItem(containsString("priceEur must be greater than or equal to 0"))))
                .andExpect(jsonPath("$", hasItem(containsString("priceHrk must be greater than or equal to 0"))))
                .andExpect(jsonPath("$", hasItem(containsString("code must not be blank"))))
                .andExpect(jsonPath("$", hasItem(containsString("name must not be blank"))))
                .andExpect(jsonPath("$", hasItem(containsString("quantity must be greater than or equal to 0"))));
        Mockito.verify(this.productMapper, never()).toModel(productDto);
        Mockito.verify(this.productRepository, times(Constants.FAKE_DATA_COUNT)).save(Mockito.any(ProductModel.class));
    }

}