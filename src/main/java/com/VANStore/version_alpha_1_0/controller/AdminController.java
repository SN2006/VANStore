package com.VANStore.version_alpha_1_0.controller;

import com.VANStore.version_alpha_1_0.models.Image;
import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.models.Product;
import com.VANStore.version_alpha_1_0.security.PersonDetails;
import com.VANStore.version_alpha_1_0.services.CarModelsService;
import com.VANStore.version_alpha_1_0.services.OrdersService;
import com.VANStore.version_alpha_1_0.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductsService productsService;
    private final CarModelsService carModelsService;
    private final OrdersService ordersService;

    @Autowired
    public AdminController(ProductsService productsService, CarModelsService carModelsService, OrdersService ordersService) {
        this.productsService = productsService;
        this.carModelsService = carModelsService;
        this.ordersService = ordersService;
    }

    @GetMapping("")
    public String panel(@ModelAttribute("newProduct") Product product, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();

        List<Product> products = productsService.findByPerson(person);

        model.addAttribute("person", person);
        model.addAttribute("products", products);
        model.addAttribute("models", carModelsService.findAll());
        model.addAttribute("orders", ordersService.findBySellerId(person.getId()));


        return "admin/newAdminPage";
    }

    @GetMapping("/editProduct/{productId}")
    public String editProductPage(@PathVariable("productId") Long productId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();

        Product product = productsService.findById(productId);
        List<Image> images = productsService.imagesByProduct(product);

        if (person.getId() != product.getPerson().getId()) return "redirect:/";

        model.addAttribute("product", product);
        model.addAttribute("images", images);
        model.addAttribute("models", carModelsService.findAll());

        return "admin/productEditPage";
    }
}
