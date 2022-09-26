package hr.tnebes.crud.controllers.impl;

import hr.tnebes.crud.controllers.ProductController;
import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.utils.Constants;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(Constants.API_V1 + "/" + Constants.PRODUCT_ENTITY_NAME)
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    @Override
    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping(value = "/{ids}")
    @Override
    public List<ProductModel> getProductsByIds(@PathVariable(required = true, name = "ids") final String ids) {
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
    public List<ProductModel> getProductsByCodes(@PathVariable(required = true, name="codes") final String code) {
        if (code == null || code.isBlank()) {
            return Collections.emptyList();
        }
        List<String> codes = Arrays.stream(code.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .filter(s -> s.length() != ProductModel.CODE_LENGTH)
                .toList();
        if (codes.isEmpty()) {
            return Collections.emptyList();
        }
        if (codes.size() == 1) {
            return Collections.singletonList(productRepository.findByCode(codes.get(0)));
        }
        return productRepository.findAllByCodes(codes);
    }

    @GetMapping(value = "/name/{name}")
    @Override
    public List<ProductModel> getProductsByName(String name) {
        return null;
    }

    @GetMapping(value = "/priceHrk/{priceHrk}")
    @Override
    public List<ProductModel> getProductsByPriceHrk(BigDecimal priceHrk) {
        return null;
    }

    @GetMapping(value = "/priceEur/{priceEur}")
    @Override
    public List<ProductModel> getProductsByPriceEur(BigDecimal priceEur) {
        return null;
    }

    @GetMapping(value = "/description/{description}")
    @Override
    public List<ProductModel> getProductsByDescription(String description) {
        return null;
    }

    @GetMapping(value = "/availability/{isAvailable}")
    @Override
    public List<ProductModel> getProductsByAvailability(Boolean isAvailable) {
        return null;
    }
}
