package hr.tnebes.crud.repository;

import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.utils.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE p.code = ?1")
    ProductModel findByCode(final String s);

    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE p.code IN ?1")
    List<ProductModel> findAllByCodes(final List<String> codes);

    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE " + Constants.PRODUCT_PRICE_HRK_COLUMN_NAME + " = ?1")
    List<ProductModel> findAllByPriceHrk(BigDecimal bigDecimalPrice);

    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE " + Constants.PRODUCT_PRICE_EUR_COLUMN_NAME + " = ?1")
    List<ProductModel> findAllByPriceEur(BigDecimal bigDecimalPrice);

    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE " + Constants.PRODUCT_IS_AVAILABLE_COLUMN_NAME + " = ?1")
    List<ProductModel> findAllByIsAvailable(Boolean isAvailable);
}