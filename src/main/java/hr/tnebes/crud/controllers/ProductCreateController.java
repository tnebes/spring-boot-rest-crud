package hr.tnebes.crud.controllers;

import hr.tnebes.crud.dtos.ProductDto;
import hr.tnebes.crud.models.ProductModel;
import org.springframework.http.ResponseEntity;

public interface ProductCreateController {
    ResponseEntity<ProductModel> createProduct(ProductDto productDto);
}
