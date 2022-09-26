package hr.tnebes.crud.services.impl;

import com.github.javafaker.Faker;
import hr.tnebes.crud.models.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.FakerService;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.stream.IntStream;

@Service
public class FakerServiceImpl implements FakerService {

    private static final Locale CURRENT_LOCALE = getLocale();
    private static final String CURRENT_LOCALE_NAME = "hr-HR";
    private static final Faker faker = new Faker(getLocale());

    private static Locale getLocale() {
        return Locale.forLanguageTag(CURRENT_LOCALE_NAME);
    }

    private static BigDecimal localiseReturnBigDecimal(String inputDecimalNumber) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(CURRENT_LOCALE);
        String decimalSeparator = String.valueOf(decimalFormatSymbols.getDecimalSeparator());
        return new BigDecimal(inputDecimalNumber.replace(decimalSeparator, "."));
    }

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void generateProducts(int count) {
        IntStream.range(0, count).forEach(i -> {
            this.productRepository.save(
                    new ProductModel(
                            faker.code().ean8(),
                            faker.commerce().productName(),
                            localiseReturnBigDecimal(faker.commerce().price()),
                            localiseReturnBigDecimal(faker.commerce().price()),
                            faker.lorem().sentence(),
                            faker.bool().bool()
                    )
            );
        });
    }
}
