package ru.rabtra.baranoffShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rabtra.baranoffShop.model.Subcategory;

import java.util.List;

@Repository
public interface SubcategoryRepository  extends JpaRepository<Subcategory, Long> {

    List<Subcategory> findByCategoryId(Long id);

}
