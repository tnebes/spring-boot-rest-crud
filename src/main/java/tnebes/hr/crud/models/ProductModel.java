package tnebes.hr.crud.models;

import java.math.BigDecimal;

public interface ProductModel {

    long getId();

    String getCode();

    String getName();

    BigDecimal getPriceHrk();

    BigDecimal getPriceEur();

    String getDescription();

    boolean isAvailable();

}
