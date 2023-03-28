package hr.tnebes.crud.services.impl;

import hr.tnebes.crud.services.ProductService;
import hr.tnebes.crud.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public BigDecimal stringToBigDecimal(String inputDecimalNumber) {
        if (!this.validateInputString(inputDecimalNumber)) {
            throw new IllegalArgumentException("Invalid input decimal number: " + inputDecimalNumber);
        }
        return new BigDecimal(inputDecimalNumber.replace(",", ""));
    }

    private boolean validateInputString(final String inputDecimalNumber) {
        if (StringUtils.isBlank(inputDecimalNumber)) {
            return false;
        }
        return inputDecimalNumber.matches(Constants.CURRENCY_REGEX_PATTERN);
    }

}