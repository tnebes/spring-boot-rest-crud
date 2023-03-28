package hr.tnebes.crud.utils;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class LocaleUtil {
    public static final Locale CURRENT_LOCALE = getLocale();

    public static Locale getLocale() {
        return Locale.forLanguageTag(Constants.CURRENT_LOCALE_NAME);
    }

    public static BigDecimal localiseReturnBigDecimal(String inputDecimalNumber) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(CURRENT_LOCALE);
        String decimalSeparator = String.valueOf(decimalFormatSymbols.getDecimalSeparator());
        return new BigDecimal(inputDecimalNumber.replace(decimalSeparator, "."));
    }
}
