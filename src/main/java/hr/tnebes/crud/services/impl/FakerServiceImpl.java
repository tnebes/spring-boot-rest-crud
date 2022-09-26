package hr.tnebes.crud.services.impl;

import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.utils.FakerUtil;
import hr.tnebes.crud.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.FakerService;

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
}