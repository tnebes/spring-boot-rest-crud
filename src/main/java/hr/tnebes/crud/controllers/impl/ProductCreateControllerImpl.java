package hr.tnebes.crud.controllers.impl;

import hr.tnebes.crud.controllers.ProductCreateController;
import hr.tnebes.crud.dtos.ProductDto;
import hr.tnebes.crud.mappers.ProductMapper;
import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = Constants.API_V1 + "/" + Constants.PRODUCT_ENTITY_NAME, produces = "application/json", consumes = "application/json")
public class ProductCreateControllerImpl implements ProductCreateController {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Autowired
    ProductCreateControllerImpl(final ProductRepository productRepository, final ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    @PostMapping(path = "/create")
    public ResponseEntity<ProductModel> createProduct(@RequestBody @Valid final ProductDto productDto) {
        try {
            final ProductModel productModel = this.productMapper.toModel(productDto);
            if (this.productRepository.findByCode(productModel.getCode()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(this.productRepository.save(productModel));
        } catch (final ConstraintViolationException e) {
            log.error("Received invalid product: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
        catch (final RuntimeException e) {
            log.error("Error while creating product: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

}