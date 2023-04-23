package hr.tnebes.crud.services;

import hr.tnebes.crud.models.ProductModel;

import java.util.List;

public interface FakerService {

    List<ProductModel> generatePredeterminedFakeTestProductList();

    void generateFakeProducts(int count);

    void generateTestProducts();
}