package ru.rabtra.baranoffShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rabtra.baranoffShop.service.CategoryService;
import ru.rabtra.baranoffShop.service.ProductService;

@Controller
public class SearchController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public SearchController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        model.addAttribute("query", query);
        model.addAttribute("products", productService.search(query));
        model.addAttribute("categories", categoryService.findAll());
        return "search-results";
    }
}
