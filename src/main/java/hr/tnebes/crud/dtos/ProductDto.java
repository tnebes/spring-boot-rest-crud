package hr.tnebes.crud.dtos;

import hr.tnebes.crud.models.product.availability.ProductAvailability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotNull
    @PositiveOrZero
    private BigDecimal priceHrk;

    @NotNull
    @PositiveOrZero
    private BigDecimal priceEur;

    private String description;

    @NotNull
    private ProductAvailability availability;

    @NotNull
    @PositiveOrZero
    private Integer quantity;

}
