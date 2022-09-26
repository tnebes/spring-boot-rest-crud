package hr.tnebes.crud.controllers.impl;

import hr.tnebes.crud.controllers.ProductController;
import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.utils.Constants;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
