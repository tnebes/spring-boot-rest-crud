package hr.tnebes.crud.controllers.impl;

import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerImplTest {

    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProductControllerImpl(this.productRepository, this.productService)).build();
    }

    @Test
    void test() throws Exception {
        this.mockMvc.perform(get("/products")).andExpect(status().isOk());
    }

}