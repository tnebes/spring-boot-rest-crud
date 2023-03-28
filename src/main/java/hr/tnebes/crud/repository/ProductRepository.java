package hr.tnebes.crud.repository;

import hr.tnebes.crud.models.ProductModel;
import hr.tnebes.crud.models.product.availability.ProductAvailability;
import hr.tnebes.crud.utils.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE p.code = :code")
    ProductModel findByCode(@Param("code") final String s);

    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE p.code IN :codes")
    List<ProductModel> findAllByCodes(@Param("codes") final List<String> codes);

    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE LOWER(p.name) LIKE %:name%")
    List<ProductModel> findAllByName(@Param("name") final String name);

    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE LOWER(p.description) LIKE %:description%")
    List<ProductModel> findAllByDescription(@Param("description") final String description);

    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE " + Constants.PRODUCT_PRICE_HRK_COLUMN_NAME + " = :price")
    List<ProductModel> findAllByPriceHrk(@Param("price") BigDecimal bigDecimalPrice);

    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE " + Constants.PRODUCT_PRICE_EUR_COLUMN_NAME + " = :price")
    List<ProductModel> findAllByPriceEur(@Param("price") BigDecimal bigDecimalPrice);

    @Query("SELECT p FROM " + Constants.PRODUCT_ENTITY_NAME + " p WHERE " + Constants.PRODUCT_IS_AVAILABLE_COLUMN_NAME + " = :availability")
    List<ProductModel> findAllByAvailability(@Param("availability") ProductAvailability availability);

}