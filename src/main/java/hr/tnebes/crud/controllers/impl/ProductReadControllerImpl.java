package hr.tnebes.crud.controllers.impl;

import hr.tnebes.crud.controllers.ProductReadController;
import hr.tnebes.crud.mappers.ProductMapper;
import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.models.product.availability.ProductAvailability;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.ProductService;
import hr.tnebes.crud.utils.Constants;
import hr.tnebes.crud.utils.CurrencyUtil;
import hr.tnebes.crud.utils.PriceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = Constants.API_V1 + "/" + Constants.PRODUCT_ENTITY_NAME, produces = "application/json")
public class ProductReadControllerImpl implements ProductReadController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    private final ProductMapper productMapper;

    @Autowired
    ProductReadControllerImpl(final ProductRepository productRepository, final ProductService productService, final ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    @Override
    public List<ProductModel> getAllProducts() {
        return this.productRepository.findAll();
    }

    @GetMapping(value = "/{ids}")
    @Override
    public List<ProductModel> getProductsByIds(@PathVariable(name = "ids") final String ids) {
        if (!ids.matches(Constants.ID_REGEX_PATTERN)) {
            return Collections.emptyList();
        }
        final List<Long> idsList = Arrays.stream(StringUtils.split(ids, ",")).map(Long::parseLong).toList();
        return this.productRepository.findAllById(idsList);
    }

    @GetMapping(value = "/codes/{codes}")
    @Override
    public List<ProductModel> getProductsByCodes(@PathVariable(name = "codes") final String code) {
        if (StringUtils.isBlank(code)) {
            return Collections.emptyList();
        }

        final List<String> codes = Arrays.stream(code.split(","))
                .map(StringUtils::trim)
                .filter(s -> !s.isBlank())
                .filter(s -> s.length() == Constants.PRODUCT_CODE_LENGTH)
                .toList();

        return this.productRepository.findAllByCodes(codes);
    }

    @GetMapping(value = "/name/{name}")
    @Override
    public List<ProductModel> getProductsByName(@PathVariable(name = "name") final String name) {
        if (StringUtils.isBlank(name)) {
            return Collections.emptyList();
        }
        return this.productRepository.findAllByName(StringUtils.toRootLowerCase(StringUtils.trim(name)));
    }

    @GetMapping(value = "/priceHrk/{priceHrk}")
    @Override
    public List<ProductModel> getProductsByPriceHrk(@PathVariable(name = "priceHrk") final String priceHrk) {
        try {
            return this.getProductsByPrice(priceHrk, CurrencyUtil.Currency.HRK);
        } catch (final RuntimeException e) {
            log.error("Error while getting products by price ", e);
            return Collections.emptyList();
        }
    }

    @GetMapping(value = "/priceEur/{priceEur}")
    @Override
    public List<ProductModel> getProductsByPriceEur(@PathVariable(name = "priceEur") final String priceEur) {
        try {
            return this.getProductsByPrice(priceEur, CurrencyUtil.Currency.EUR);
        } catch (final RuntimeException e) {
            log.error("Error while getting products by price ", e);
            return Collections.emptyList();
        }
    }

    @GetMapping(value = "/description/{description}")
    @Override
    public List<ProductModel> getProductsByDescription(@PathVariable(name = "description") final String description) {
        if (StringUtils.isBlank(description)) {
            return Collections.emptyList();
        }
        return this.productRepository.findAllByDescription(StringUtils.toRootLowerCase(StringUtils.trim(description)));
    }

    @GetMapping(value = "/availability/{availability}")
    @Override
    public List<ProductModel> getProductsByAvailability(@PathVariable(name = "availability") final String availability) {
        if (StringUtils.isBlank(availability)) {
            return Collections.emptyList();
        }
        try {
            final ProductAvailability productAvailability = ProductAvailability.valueOf(StringUtils.toRootUpperCase(StringUtils.trim(availability)));
            return this.productRepository.findAllByAvailability(productAvailability);
        } catch (final IllegalArgumentException e) {
            log.error("Invalid availability: {}", availability);
            return Collections.emptyList();
        }
    }

    private List<ProductModel> getProductsByPrice(final String price, final CurrencyUtil.Currency selectedCurrency) {
        final BigDecimal bigDecimalPrice = PriceUtil.stringToBigDecimal(price);
        return switch (selectedCurrency) {
            case HRK -> this.productRepository.findAllByPriceHrk(bigDecimalPrice);
            case EUR -> this.productRepository.findAllByPriceEur(bigDecimalPrice);
        };
    }

}