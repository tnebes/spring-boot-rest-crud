package hr.tnebes.crud.models;

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
    @Column(length = 10)
    private String code;

    @NonNull
    private String name;

    @NonNull
    private BigDecimal priceHrk;

    @NonNull
    private BigDecimal priceEur;

    @NonNull
    private String description;

    @NonNull
    @Column(name = "is_available")
    private Boolean available;

}