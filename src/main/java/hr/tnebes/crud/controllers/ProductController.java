package hr.tnebes.crud.controllers;

import hr.tnebes.crud.models.ProductModel;

import java.util.List;

public interface ProductController {

    List<ProductModel> getAllProducts();

    List<ProductModel> getProductsByIds(final String ids);

    List<ProductModel> getProductsByCodes(final String code);

    List<ProductModel> getProductsByName(final String name);

    List<ProductModel> getProductsByPriceHrk(final String priceHrk);

    List<ProductModel> getProductsByPriceEur(final String priceEur);

    List<ProductModel> getProductsByDescription(final String description);

    List<ProductModel> getProductsByAvailability(final Boolean isAvailable);

}