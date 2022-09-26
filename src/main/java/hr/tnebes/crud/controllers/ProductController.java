package hr.tnebes.crud.controllers;

import hr.tnebes.crud.models.ProductModel;

import java.util.List;

public interface ProductController {

    List<ProductModel> getAllProducts();

    List<ProductModel> getProductsByIds(String ids);

}
