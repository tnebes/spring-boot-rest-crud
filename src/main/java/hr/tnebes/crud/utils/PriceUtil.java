package hr.tnebes.crud.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceUtil {

    private PriceUtil() {
    }

    public static BigDecimal stringToBigDecimal(String inputDecimalNumber) {
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
