package hr.tnebes.crud.controllers.impl;

import hr.tnebes.crud.controllers.impl.ProductControllerImpl;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.FakerService;
import hr.tnebes.crud.services.ProductService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductControllerImpl.class)
class ProductControllerImplTest {

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
        Mockito.when(this.productRepository.findAll()).thenReturn(this.fakerService.generateFakeTestProductList());
    }

    @Test
    void test() throws Exception {
        this.mockMvc.perform(get("/api/v1/product")).andExpect(status().isOk());
    }
}