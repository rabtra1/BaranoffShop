package ru.rabtra.baranoffShop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rabtra.baranoffShop.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    @Query("""
        SELECT p
        FROM Product p
        WHERE p.subcategory.category.id = :categoryId
    """)
    List<Product> findByCategoryId(Long categoryId);

    List<Product> findBySubcategoryId(Long id);

    @Query(
            """
            SELECT p
            FROM Product p
            WHERE p.subcategory.id = :subcategoryId and p.id != :itemId
    """)
    List<Product> findBySubcategoryIdWithoutCurrent(Long subcategoryId, Long itemId);

    @Query("SELECT p FROM Product p " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "ORDER BY " +
            "  CASE WHEN LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) THEN 1 ELSE 2 END, " +
            "  p.name")
    List<Product> searchProduct(@Param("query") String query);

    @Query("SELECT p FROM Product p " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "ORDER BY " +
            "  CASE WHEN LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) THEN 1 ELSE 2 END, " +
            "  p.name")
    Page<Product> searchProduct(Pageable pageable, @Param("query") String query);
}
