package hr.tnebes.crud.services.impl;

import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.utils.FakerUtil;
import hr.tnebes.crud.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.FakerService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class FakerServiceImpl implements FakerService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void generateProducts(int count) {
        IntStream.range(0, count).forEach(i -> {
            this.productRepository.save(
                    new ProductModel(
                            FakerUtil.faker.code().ean8(),
                            FakerUtil.faker.commerce().productName(),
                            Util.localiseReturnBigDecimal(FakerUtil.faker.commerce().price()),
                            Util.localiseReturnBigDecimal(FakerUtil.faker.commerce().price()),
                            FakerUtil.faker.lorem().sentence(),
                            FakerUtil.faker.bool().bool()
                    )
            );
        });
    }

    @Override
    public void generateTestProducts() {
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel("1234567800", "Test product 1", new BigDecimal("1.00"), new BigDecimal("0.50"), "", true));
        products.add(new ProductModel("1234567900", "Test product 2", new BigDecimal("2.00"), new BigDecimal("1.00"), "Test product 2 description", false));
        products.add(new ProductModel("1234567000", "Test product 3", new BigDecimal("3.00"), new BigDecimal("1.50"), "Test product 3 description", true));
        products.add(new ProductModel("1234567100", "Test product 4", new BigDecimal("4.00"), new BigDecimal("2.00"), "Test product 4 description", false));
        products.add(new ProductModel("1234567200", "Test product 5", new BigDecimal("5.00"), new BigDecimal("2.50"), "Test product 5 description", true));
        products.add(new ProductModel("1234567300", "Test product 6", new BigDecimal("6.00"), new BigDecimal("3.00"), "Test product 6 description", false));
        products.add(new ProductModel("1234567400", "Test product 7", new BigDecimal("7.00"), new BigDecimal("3.50"), "Test product 7 description", true));
        products.add(new ProductModel("1234567500", "Test product 8", new BigDecimal("8.00"), new BigDecimal("4.00"), "Test product 8 description", false));
        products.add(new ProductModel("1234567600", "Test product 9", new BigDecimal("9.00"), new BigDecimal("4.50"), "Test product 9 description", true));
        this.productRepository.saveAll(products);
    }
}