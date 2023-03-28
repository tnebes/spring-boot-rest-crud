package hr.tnebes.crud.models;

import hr.tnebes.crud.models.product.availability.ProductAvailability;
import hr.tnebes.crud.utils.Constants;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static hr.tnebes.crud.utils.Constants.PRODUCT_ENTITY_NAME;

@Entity(name = PRODUCT_ENTITY_NAME)
@Table(indexes = {@Index(columnList = "code"), @Index(columnList = "availability")})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = Constants.PRODUCT_CODE_LENGTH)
    private String code;

    @NonNull
    private String name;

    @NonNull
    @DecimalMin("0.01")
    private BigDecimal priceHrk;

    @NonNull
    @DecimalMin("0.01")
    private BigDecimal priceEur;

    @NonNull
    private String description;

    @Enumerated(EnumType.STRING)
    @NonNull
    private ProductAvailability availability;

    @NonNull
    private Integer quantity;

    public ProductModel(@NonNull String name, @NonNull String code, @NonNull BigDecimal priceHrk,
                        @NonNull BigDecimal priceEur, @NonNull String description, @NonNull ProductAvailability availability,
                        @NonNull Integer quantity) {
        this.name = name;
        this.code = code;
        this.priceHrk = priceHrk;
        this.priceEur = priceEur;
        this.description = description;
        this.availability = availability;
        this.quantity = quantity;
    }

}

