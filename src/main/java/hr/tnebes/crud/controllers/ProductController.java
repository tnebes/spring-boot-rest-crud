package hr.tnebes.crud.controllers;

import hr.tnebes.crud.models.ProductModel;

import java.math.BigDecimal;
import java.util.List;

public interface ProductController {

    List<ProductModel> getAllProducts();

    List<ProductModel> getProductsByIds(final String ids);

    List<ProductModel> getProductsByCodes(final String code);

    List<ProductModel> getProductsByName(final String name);

    List<ProductModel> getProductsByPriceHrk(final BigDecimal priceHrk);

    List<ProductModel> getProductsByPriceEur(final BigDecimal priceEur);

    List<ProductModel> getProductsByDescription(final String description);

    List<ProductModel> getProductsByAvailability(final Boolean isAvailable);

}
