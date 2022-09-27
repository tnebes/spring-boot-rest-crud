package hr.tnebes.crud.utils;

import javax.persistence.Transient;

public class Constants {
    public static final String CURRENT_LOCALE_NAME = "hr-HR";

    public static final String PRODUCT_ENTITY_NAME = "product";

    public static final String API_V1 = "/api/v1";

    public static final String ID_REGEX_PATTERN = "^[0-9,]+$";
    public static final String CURRENCY_REGEX_PATTERN = "^(\\d{1,3}(,\\d{3})*|(\\d+))(\\.\\d{2}||(\\d+))(\\.\\d{1})?$";
    @Transient
    public static final int PRODUCT_CODE_LENGTH = 10;
    @Transient
    public static final String PRODUCT_PRICE_HRK_COLUMN_NAME = "price_hrk";
    @Transient
    public static final String PRODUCT_PRICE_EUR_COLUMN_NAME = "price_eur";
    public static final String PRODUCT_IS_AVAILABLE_COLUMN_NAME = "is_available";

}