package hr.tnebes.crud.services.impl;

import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.FakerService;
import hr.tnebes.crud.utils.FakerUtil;
import hr.tnebes.crud.utils.Util;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static hr.tnebes.crud.models.product.availability.ProductAvailability.AVAILABLE;
import static hr.tnebes.crud.models.product.availability.ProductAvailability.NOT_AVAILABLE;

@Service
public class FakerServiceImpl implements FakerService {

    private final ProductRepository productRepository;

    public FakerServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductModel> generateFakeTestProductList() {
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel( 1L, "1234567800", "Test product 1", new BigDecimal("1.00"), new BigDecimal("0.50"), "", AVAILABLE, 1));
        products.add(new ProductModel( 2L, "1234567801", "Test product 2", new BigDecimal("2.00"), new BigDecimal("1.00"), "", NOT_AVAILABLE, 2));
        products.add(new ProductModel( 3L, "1234567802", "Test product 3", new BigDecimal("3.00"), new BigDecimal("1.50"), "", AVAILABLE, 3));
        products.add(new ProductModel( 4L, "1234567803","Test product 4", new BigDecimal("4.00"), new BigDecimal("2.00"), "", NOT_AVAILABLE, -1));
        products.add(new ProductModel( 5L, "1234567804", "Test product 5", new BigDecimal("5.00"), new BigDecimal("2.50"), "", AVAILABLE, 6));
        products.add(new ProductModel( 6L, "1234567805", "Test product 6", new BigDecimal("6.00"), new BigDecimal("3.00"), "", NOT_AVAILABLE, 6));
        products.add(new ProductModel( 7L, "1234567806", "Test product 7", new BigDecimal("7.00"), new BigDecimal("3.50"), "", AVAILABLE, 10));
        products.add(new ProductModel( 8L, "1234567807", "Test product 8", new BigDecimal("8.00"), new BigDecimal("4.00"), "", NOT_AVAILABLE, 11));
        products.add(new ProductModel( 9L, "1234567808", "Test product 9", new BigDecimal("9.00"), new BigDecimal("4.50"), "", AVAILABLE, 15));
        return products;
    }

    @Override
    public void generateFakeProducts(int count) {
        IntStream.range(0, count).forEach(i -> this.productRepository.save(
                new ProductModel((long) i,FakerUtil.faker.code().ean8(),
                        FakerUtil.faker.commerce().productName(),
                        Util.localiseReturnBigDecimal(FakerUtil.faker.commerce().price()),
                        Util.localiseReturnBigDecimal(FakerUtil.faker.commerce().price()),
                        FakerUtil.faker.lorem().sentence(),
                        FakerUtil.faker.bool().bool() ? AVAILABLE : NOT_AVAILABLE,
                        FakerUtil.faker.number().numberBetween(0, 100))
                ));
    }

    @Override
    public void generateTestProducts() {
        List<ProductModel> products = this.generateFakeTestProductList();
        this.productRepository.saveAll(products);
    }
}