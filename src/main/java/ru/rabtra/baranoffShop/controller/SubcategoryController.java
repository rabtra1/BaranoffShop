package ru.rabtra.baranoffShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rabtra.baranoffShop.service.CategoryService;
import ru.rabtra.baranoffShop.service.ProductService;
import ru.rabtra.baranoffShop.service.SubcategoryService;

@Controller
@RequestMapping("/subcategory")
public class SubcategoryController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public SubcategoryController(ProductService productService, SubcategoryService subcategoryService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id, Model model) {
        model.addAttribute("items", productService.findBySubcategoryId(id));
        model.addAttribute("categories", categoryService.findAll());
        return "category/subcategory";
    }

}
