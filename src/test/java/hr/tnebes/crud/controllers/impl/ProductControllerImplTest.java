package hr.tnebes.crud.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.services.FakerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {FakerService.class})
@WebMvcTest
class ProductControllerImplTest {

    @Autowired
    private FakerService fakerService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach()
    void setUpBeforeAll() {
        fakerService.generateTestProducts();
    }

    @Test
    @DisplayName("GIVEN a list of 9 products WHEN /product/ is called THEN return 9 products.")
    void testGetAllProductsReturns9Products() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/product/"))
                .andExpect(status().isOk())
                .andReturn();

        List<ProductModel> productModels = Arrays.asList(this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductModel[].class));
        Assertions.assertEquals(9, productModels.size());
    }


}