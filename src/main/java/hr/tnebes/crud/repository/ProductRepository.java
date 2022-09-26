package hr.tnebes.crud.repository;

import hr.tnebes.crud.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}