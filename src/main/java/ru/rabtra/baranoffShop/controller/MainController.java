package ru.rabtra.baranoffShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rabtra.baranoffShop.service.CategoryService;
import ru.rabtra.baranoffShop.service.ProductService;
import ru.rabtra.baranoffShop.service.SubcategoryService;

import java.util.List;

@Controller
public class MainController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public MainController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    public String index(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size
    ) {
        Integer allPages = productService.findAll().size()/size;
        model.addAttribute("products", productService.findAll(PageRequest.of(page, size)).getContent());
        model.addAttribute("page", page);
        model.addAttribute("hasNext", page <= allPages);
        model.addAttribute("allPages", allPages);
        model.addAttribute("categories", categoryService.findAll());

        return "index";
    }



}
