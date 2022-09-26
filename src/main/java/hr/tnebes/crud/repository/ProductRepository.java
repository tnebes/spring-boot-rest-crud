package hr.tnebes.crud.repository;

import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.utils.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE p.code = ?1")
    ProductModel findByCode(final String s);

    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE p.code IN ?1")
    List<ProductModel> findAllByCodes(final List<String> codes);
}