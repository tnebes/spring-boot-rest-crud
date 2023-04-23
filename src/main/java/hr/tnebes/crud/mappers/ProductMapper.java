package hr.tnebes.crud.mappers;

import hr.tnebes.crud.dtos.ProductDto;
import hr.tnebes.crud.models.ProductModel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class ProductMapper {

    private final Validator validator;

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
                productModel.getId(),
                productModel.getCode(),
                productModel.getName(),
                productModel.getPriceHrk(),
                productModel.getPriceEur(),
                productModel.getDescription(),
                productModel.getAvailability(),
                productModel.getQuantity());
    }
}
