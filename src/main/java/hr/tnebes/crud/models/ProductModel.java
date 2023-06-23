package hr.tnebes.crud.models;

import hr.tnebes.crud.models.product.availability.ProductAvailability;
import hr.tnebes.crud.utils.Constants;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

import static hr.tnebes.crud.utils.Constants.PRODUCT_ENTITY_NAME;

@Entity(name = PRODUCT_ENTITY_NAME)
@Table(indexes = {@Index(columnList = "code"), @Index(columnList = "availability")})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(length = Constants.PRODUCT_CODE_LENGTH, unique = true)
    @NotNull
    private String code;

    @NonNull
    @NotBlank
    private String name;

    @NonNull
    @DecimalMin("0.01")
    @NotNull
    @PositiveOrZero
    private BigDecimal priceHrk;

    @NonNull
    @DecimalMin("0.01")
    @NotNull
    @PositiveOrZero
    private BigDecimal priceEur;

    @NonNull
    @NotBlank
    private String description;

    @Enumerated(EnumType.STRING)
    @NonNull
    @NotNull
    private ProductAvailability availability;

    @NonNull
    @NotNull
    private Integer quantity;

}

