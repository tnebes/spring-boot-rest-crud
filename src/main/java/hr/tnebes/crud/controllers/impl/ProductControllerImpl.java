package hr.tnebes.crud.controllers.impl;

import hr.tnebes.crud.controllers.ProductController;
import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.ProductService;
import hr.tnebes.crud.utils.Constants;
import hr.tnebes.crud.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController()
@RequestMapping(Constants.API_V1 + "/" + Constants.PRODUCT_ENTITY_NAME)
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping
    @Override
    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping(value = "/{ids}")
    @Override
    public List<ProductModel> getProductsByIds(@PathVariable(name = "ids") final String ids) {
        if (!ids.matches(Constants.ID_REGEX_PATTERN)) {
            return Collections.emptyList();
        }
        List<Long> idsList = Arrays.stream(ids.split(",")).map(Long::parseLong).toList();
        if (idsList.isEmpty()) {
            return Collections.emptyList();
        }
        if (idsList.size() == 1) {
            return Collections.singletonList(productRepository.findById(idsList.get(0)).orElse(null));
        }
        return productRepository.findAllById(idsList);
    }

    @GetMapping(value = "/codes/{codes}")
    @Override
    public List<ProductModel> getProductsByCodes(@PathVariable(name = "codes") final String code) {
        if (code == null || code.isBlank()) {
            return Collections.emptyList();
        }
        List<String> codes = Arrays.stream(code.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .filter(s -> s.length() != Constants.PRODUCT_CODE_LENGTH)
                .toList();
        if (codes.isEmpty()) {
            return Collections.emptyList();
        }
        if (codes.size() == 1) {
            return Collections.singletonList(productRepository.findByCode(codes.get(0)));
        }
        return productRepository.findAllByCodes(codes);
    }

    @GetMapping(value = "/names/{names}")
    @Override
    public List<ProductModel> getProductsByName(@PathVariable(name = "names") final String names) {
        if (names == null || names.isBlank()) {
            return Collections.emptyList();
        }
        List<String> namesList = Arrays.stream(names.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(s -> s.toLowerCase())
                .toList();
        if (namesList.isEmpty()) {
            return Collections.emptyList();
        }
        if (namesList.size() == 1) {
            throw new UnsupportedOperationException("Not implemented yet");
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping(value = "/priceHrk/{priceHrk}")
    @Override
    public List<ProductModel> getProductsByPriceHrk(@PathVariable(name = "priceHrk") final String priceHrk) {
        return this.getProductsByPrice(priceHrk, Util.Currency.HRK);
    }

    @GetMapping(value = "/priceEur/{priceEur}")
    @Override
    public List<ProductModel> getProductsByPriceEur(@PathVariable(name = "priceEur") final String priceEur) {
        return this.getProductsByPrice(priceEur, Util.Currency.EUR);
    }

    @GetMapping(value = "/description/{description}")
    @Override
    public List<ProductModel> getProductsByDescription(@PathVariable(name = "description") final String description) {
        return null;
    }

    @GetMapping(value = "/availability/{isAvailable}")
    @Override
    public List<ProductModel> getProductsByAvailability(@PathVariable(name = "isAvailable") final Boolean isAvailable) {
        if (isAvailable == null) {
            return Collections.emptyList();
        }
        return productRepository.findAllByIsAvailable(isAvailable);
    }

    private List<ProductModel> getProductsByPrice(final String price, final Util.Currency selectedCurrency) {
        BigDecimal bigDecimalPrice = this.productService.stringToBigDecimal(price);
        if (bigDecimalPrice == null) {
            return Collections.emptyList();
        }
        return switch (selectedCurrency) {
            case HRK -> productRepository.findAllByPriceHrk(bigDecimalPrice);
            case EUR -> productRepository.findAllByPriceEur(bigDecimalPrice);
            default -> Collections.emptyList();
        };
    }

}
