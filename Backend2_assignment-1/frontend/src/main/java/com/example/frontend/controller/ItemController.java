package com.example.frontend.controller;


import com.example.frontend.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("")
    public String displayAllProducts(Model model) {
        model.addAttribute("products", itemService.getAllProducts());
        return "./products/allProducts";
    }


}
