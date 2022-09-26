package hr.tnebes.crud.services.impl;

import hr.tnebes.crud.services.ProductService;
import hr.tnebes.crud.utils.Constants;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public BigDecimal stringToBigDecimal(String inputDecimalNumber) {
        if (this.validateInputString(inputDecimalNumber)) {
            return new BigDecimal(inputDecimalNumber);
        } else {
            return null;
        }
    }

    private boolean validateInputString(final String inputDecimalNumber) {
        if (inputDecimalNumber == null || inputDecimalNumber.isEmpty()) {
            return false;
        }
        return inputDecimalNumber.matches(Constants.CURRENCY_REGEX_PATTERN);
    }

}
