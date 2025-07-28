package ru.rabtra.baranoffShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rabtra.baranoffShop.model.Subcategory;
import ru.rabtra.baranoffShop.repository.SubcategoryRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;

    @Autowired
    public SubcategoryService(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }


    public List<Subcategory> findAll() {
        return subcategoryRepository.findAll();
    }
}
