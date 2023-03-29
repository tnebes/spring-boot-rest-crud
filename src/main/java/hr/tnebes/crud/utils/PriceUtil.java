package hr.tnebes.crud.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public final class PriceUtil {

    private PriceUtil() {
    }

    public static BigDecimal stringToBigDecimal(final String inputDecimalNumber) {
        if (!PriceUtil.validateInputString(inputDecimalNumber)) {
            throw new IllegalArgumentException("Invalid input decimal number: " + inputDecimalNumber);
        }
        return new BigDecimal(inputDecimalNumber.replace(",", ""));
    }

    private static boolean validateInputString(final String inputDecimalNumber) {
        if (StringUtils.isBlank(inputDecimalNumber)) {
            return false;
        }
        return inputDecimalNumber.matches(Constants.CURRENCY_REGEX_PATTERN);
    }

}
