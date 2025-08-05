package ru.rabtra.baranoffShop.controller;

import org.springframework.data.domain.PageRequest;
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
    public String search(
            @RequestParam("query") String query,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size,
            Model model
    ) {
        Integer allPages = productService.search(query).size()/size;
        model.addAttribute("query", query);
        model.addAttribute("products", productService.search(PageRequest.of(page,size), query).getContent());
        model.addAttribute("page", page);
        model.addAttribute("hasNext", page <= allPages);
        model.addAttribute("allPages", allPages);
        model.addAttribute("categories", categoryService.findAll());
        return "search-results";
    }
}
