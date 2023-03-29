package hr.tnebes.crud.controllers.impl;

import hr.tnebes.crud.controllers.ProductController;
import hr.tnebes.crud.dtos.ProductDto;
import hr.tnebes.crud.mappers.ProductMapper;
import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.models.product.availability.ProductAvailability;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.services.ProductService;
import hr.tnebes.crud.utils.Constants;
import hr.tnebes.crud.utils.CurrencyUtil;
import hr.tnebes.crud.utils.LocaleUtil;
import hr.tnebes.crud.utils.PriceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = Constants.API_V1 + "/" + Constants.PRODUCT_ENTITY_NAME, produces = "application/json")
public class ProductControllerImpl implements ProductController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    private final ProductMapper productMapper;

    @Autowired
    ProductControllerImpl(final ProductRepository productRepository, final ProductService productService, final ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(final MethodArgumentNotValidException exception) {
        final BindingResult result = exception.getBindingResult();
        final List<String> errorMessages = result.getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .toList();
        log.error("Validation error: {}", errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
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
        final List<Long> idsList = Arrays.stream(ids.split(",")).map(Long::parseLong).toList();
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
        return this.productRepository.findAllByName(name.trim().toLowerCase(LocaleUtil.CURRENT_LOCALE));
    }

    @GetMapping(value = "/priceHrk/{priceHrk}")
    @Override
    public List<ProductModel> getProductsByPriceHrk(@PathVariable(name = "priceHrk") final String priceHrk) {
        return this.getProductsByPrice(priceHrk, CurrencyUtil.Currency.HRK);
    }

    @GetMapping(value = "/priceEur/{priceEur}")
    @Override
    public List<ProductModel> getProductsByPriceEur(@PathVariable(name = "priceEur") final String priceEur) {
        return this.getProductsByPrice(priceEur, CurrencyUtil.Currency.EUR);
    }

    @GetMapping(value = "/description/{description}")
    @Override
    public List<ProductModel> getProductsByDescription(@PathVariable(name = "description") final String description) {
        if (StringUtils.isBlank(description)) {
            return Collections.emptyList();
        }
        return this.productRepository.findAllByDescription(description.trim().toLowerCase(LocaleUtil.CURRENT_LOCALE));
    }

    @GetMapping(value = "/availability/{availability}")
    @Override
    public List<ProductModel> getProductsByAvailability(@PathVariable(name = "availability") final String availability) {
        if (StringUtils.isBlank(availability)) {
            return Collections.emptyList();
        }
        try {
            final ProductAvailability productAvailability = ProductAvailability.valueOf(availability.toUpperCase());
            return this.productRepository.findAllByAvailability(productAvailability);
        } catch (final IllegalArgumentException e) {
            // TODO: return status code 4xx
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

    @Override
    @PostMapping(consumes = "application/json", produces = "application/json", name = "createProduct", path = "/create")
    public ResponseEntity<ProductModel> createProduct(@RequestBody @Valid final ProductDto productDto) {
        try {
            final ProductModel productModel = this.productMapper.toModel(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(this.productRepository.save(productModel));
        } catch (final RuntimeException e) {
            log.error("Error while creating product: {}{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}