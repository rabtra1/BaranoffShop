package ru.rabtra.baranoffShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rabtra.baranoffShop.model.Product;
import ru.rabtra.baranoffShop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> findBySubcategoryId(Long subcategoryId) {
        return productRepository.findBySubcategoryId(subcategoryId);
    }

    public List<Product> findBySubcategoryIdWithoutCurrent(Long subcategoryId, Long itemId) {
        return productRepository.findBySubcategoryIdWithoutCurrent(subcategoryId, itemId);
    }

    public List<Product> search(String query) {
        return productRepository.searchProduct(query);
    }

}
