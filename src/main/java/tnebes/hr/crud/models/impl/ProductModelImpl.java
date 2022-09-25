package tnebes.hr.crud.models.impl;

import lombok.Getter;
import tnebes.hr.crud.models.ProductModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity(name = "Product")
@Table
@Getter
public class ProductModelImpl implements ProductModel {

    @Id
    private long id;

    @Column(length = 10)

    private String code;

    private String name;

    private BigDecimal priceHrk;

    private BigDecimal priceEur;

    private String description;

    @Column(name = "is_available")
    private boolean available;

}