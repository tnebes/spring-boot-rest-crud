package hr.tnebes.crud.mappers;

import hr.tnebes.crud.dtos.ProductDto;
import hr.tnebes.crud.models.ProductModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProductMapper {

    private final Validator validator;

    @Autowired
    ProductMapper(final Validator validator) {
        this.validator = validator;
    }

    public ProductModel toModel(final ProductDto productDto) {
        final Set<ConstraintViolation<ProductDto>> violations = this.validator.validate(productDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return new ProductModel(
                productDto.getCode(),
                productDto.getName(),
                productDto.getPriceHrk(),
                productDto.getPriceEur(),
                productDto.getDescription(),
                productDto.getAvailability(),
                productDto.getQuantity());
    }

    public ProductDto toDto(final ProductModel productModel) {
        final Set<ConstraintViolation<ProductModel>> violations = this.validator.validate(productModel);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return new ProductDto(
                productModel.getCode(),
                productModel.getName(),
                productModel.getPriceHrk(),
                productModel.getPriceEur(),
                productModel.getDescription(),
                productModel.getAvailability(),
                productModel.getQuantity());
    }
}
