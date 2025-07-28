package ru.rabtra.baranoffShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rabtra.baranoffShop.service.CategoryService;
import ru.rabtra.baranoffShop.service.ProductService;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public String category(@PathVariable("id") Long id, Model model) {
        model.addAttribute("items", productService.findByCategoryId(id));
        model.addAttribute("categories", categoryService.findAll());
        return "category/categoryItems";
    }

}
