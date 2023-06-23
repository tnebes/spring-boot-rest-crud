package hr.tnebes.crud.controllers.impl;

import hr.tnebes.crud.controllers.ProductUpdateController;
import hr.tnebes.crud.dtos.ProductDto;
import hr.tnebes.crud.mappers.ProductMapper;
import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.repository.ProductRepository;
import hr.tnebes.crud.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = Constants.API_V1 + "/" + Constants.PRODUCT_ENTITY_NAME, produces = "application/json", consumes = "application/json")
public class ProductUpdateControllerImpl implements ProductUpdateController {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Autowired
    ProductUpdateControllerImpl(final ProductMapper productMapper, final ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    @PutMapping(value = "/update")
    public ResponseEntity<ProductModel> updateProduct(@RequestBody @Valid final ProductDto productDto) {
        try {
            final Optional<ProductModel> productModel = this.productRepository.findById(productDto.getId());
            if (productModel.isEmpty()) {
                log.error("Product with id {} does not exist.", productDto.getId());
                return ResponseEntity.notFound().build();
            }
            final ProductModel product = productModel.get();
            this.productMapper.updateProductFromDto(productDto, product);
            return ResponseEntity.ok().body(this.productRepository.save(product));
        } catch (final ConstraintViolationException e) {
            log.error("Received invalid product for update: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (final RuntimeException e) {
            log.error("Error while updating product: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
