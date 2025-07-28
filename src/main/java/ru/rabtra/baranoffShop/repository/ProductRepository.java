package ru.rabtra.baranoffShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rabtra.baranoffShop.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT p
        FROM Product p
        WHERE p.subcategory.category.id = :categoryId
    """)
    List<Product> findByCategoryId(Long categoryId);

}
