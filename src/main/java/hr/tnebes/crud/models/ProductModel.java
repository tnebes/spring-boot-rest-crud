package hr.tnebes.crud.models;

import hr.tnebes.crud.utils.Constants;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static hr.tnebes.crud.utils.Constants.PRODUCT_ENTITY_NAME;

@Entity(name = PRODUCT_ENTITY_NAME)
@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(length = Constants.PRODUCT_CODE_LENGTH, unique = true)
    private String code;

    @NonNull
    private String name;

    @NonNull
    @Column(name = Constants.PRODUCT_PRICE_HRK_COLUMN_NAME)
    private BigDecimal priceHrk;

    @NonNull
    @Column(name = Constants.PRODUCT_PRICE_EUR_COLUMN_NAME)
    private BigDecimal priceEur;

    @NonNull
    private String description;

    @NonNull
    @Column(name = Constants.PRODUCT_IS_AVAILABLE_COLUMN_NAME)
    private Boolean available;

}