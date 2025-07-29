package ru.rabtra.baranoffShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rabtra.baranoffShop.model.Product;
import ru.rabtra.baranoffShop.service.ProductService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String showProduct(@PathVariable("id") Long id, Model model) {
        var byId = productService.findById(id);
        if (byId.isPresent()) {
            var product = byId.get();
            var productForSames = productService.
                    findBySubcategoryIdWithoutCurrent(product.getSubcategory().getId(), id);
            model.addAttribute("item", product);
            model.addAttribute("sameProducts", productForSames);
        }
        return "product/product";
    }

}