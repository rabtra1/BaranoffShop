package ru.rabtra.baranoffShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rabtra.baranoffShop.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
