package hr.tnebes.crud.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tnebes.crud.dtos.ProductDto;
import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.FakerService;
import hr.tnebes.crud.services.ProductService;
import hr.tnebes.crud.util.ResourceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductUpdateControllerImpl.class)
class ProductUpdateControllerImplTest {

    List<ProductModel> mockedProductList;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FakerService fakerService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUpBeforeEach() {
        this.mockedProductList = this.fakerService.generatePredeterminedFakeTestProductList();
    }

    @DisplayName("GIVEN a valid product dto with an ID that matches the id of an existing product WHEN updated THEN the article is updated in the database.")
    @Test
    void testUpdateProductUpdatesProduct() throws Exception {
        // TODO add mocks
        final Resource validProductResource = new ClassPathResource("productUpdateControllerImplTest/validProduct.json");
        this.mockMvc.perform(put("/api/v1/product/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ResourceUtil.jsonFromResource(validProductResource, ProductDto.class)))
                .andExpect(status().isOk());
    }

}