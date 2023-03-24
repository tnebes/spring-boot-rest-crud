package hr.tnebes.crud;

import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.FakerService;
import hr.tnebes.crud.services.impl.FakerServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Bean
    public FakerService fakerService(final ProductRepository productRepository) {
        return new FakerServiceImpl(productRepository);
    }

    @Bean
    @Primary
    public ProductRepository productRepository() {
        return Mockito.mock(ProductRepository.class);
    }

}
