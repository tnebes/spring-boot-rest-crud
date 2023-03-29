package hr.tnebes.crud.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tnebes.crud.TestConstants;
import hr.tnebes.crud.dtos.ProductDto;
import hr.tnebes.crud.mappers.ProductMapper;
import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.ProductService;
import hr.tnebes.crud.utils.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureMockMvc
class ProductCreateControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

//    @Test
//    void createProduct_returnsCreated() throws Exception {
//
//        final ProductDto productDto = new ProductDto();
//
//
//        final ProductModel productModel = new ProductModel();
//
//        Mockito.when(this.productMapper.toModel(productDto)).thenReturn(productModel);
//        Mockito.when(this.productRepository.save(productModel)).thenReturn(productModel);
//
//        this.mockMvc.perform(post("/api/v1/product/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(productDto)))
//                .andExpect(status().isCreated());
//
//        Mockito.verify(this.productMapper, times(1)).toModel(productDto);
//        Mockito.verify(this.productRepository, times(1)).save(productModel);
//    }

    @DisplayName("GIVEN WHEN THEN")
    @Test
    void testCreateProductReturnsExceptionOnInvalidProduct() throws Exception {
        final ProductDto productDto = TestConstants.INVALID_PRODUCT_DTO;
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> requestEntity = new HttpEntity<>(new ObjectMapper().writeValueAsString(productDto), headers);
        final URI uri = new URI("http://localhost:8080/api/v1/product/create");
        Assertions.assertAll(
                () -> Assertions.assertThrows(HttpClientErrorException.class, () -> restTemplate.postForEntity(uri, requestEntity, ProductModel.class))
        );
        Mockito.verify(this.productMapper, never()).toModel(productDto);
        Mockito.verify(this.productRepository, times(Constants.FAKE_DATA_COUNT)).save(Mockito.any(ProductModel.class));
    }

//    @Test
//    void createProduct_returnsInternalServerError_whenSaveFails() throws Exception {
//        final ProductDto productDto = new ProductDto();
//
//        final ProductModel productModel = new ProductModel();
//
//        Mockito.when(this.productMapper.toModel(productDto)).thenReturn(productModel);
//        Mockito.when(this.productRepository.save(productModel)).thenThrow(new RuntimeException());
//
//        this.mockMvc.perform(post("/api/v1/product/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(productDto)))
//                .andExpect(status().isInternalServerError());
//
//        Mockito.verify(this.productMapper, times(1)).toModel(productDto);
//        Mockito.verify(this.productRepository, times(1)).save(productModel);
//    }

}