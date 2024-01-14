package com.VANStore.version_alpha_1_0.controller;

import com.VANStore.version_alpha_1_0.models.Image;
import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.models.Product;
import com.VANStore.version_alpha_1_0.security.PersonDetails;
import com.VANStore.version_alpha_1_0.services.CarModelsService;
import com.VANStore.version_alpha_1_0.services.OrdersService;
import com.VANStore.version_alpha_1_0.services.ProductsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductsService productsService;
    private final CarModelsService carModelsService;
    private final OrdersService ordersService;

    @Autowired
    public ProductController(ProductsService productsService, CarModelsService carModelsService, OrdersService ordersService) {
        this.productsService = productsService;
        this.carModelsService = carModelsService;
        this.ordersService = ordersService;
    }

    @GetMapping("/{id}")
    public String product(@PathVariable("id") Long id,
                          Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person buyer = personDetails.getPerson();

        Product product = productsService.findById(id);
        model.addAttribute("product", productsService.findById(id));
        List<Image> images = productsService.imagesByProduct(product);
        model.addAttribute("images", images);
        model.addAttribute("buyer", buyer);
        return "/catalog/product";
    }

    @PostMapping("")
    public String createProduct(@ModelAttribute("newProduct") @Valid Product product,
                                BindingResult bindingResult,
                                @RequestParam("file") MultipartFile file,
                                @RequestParam("personId") Long personId,
                                @RequestParam("modelId") Long modelId,
                                Model model) throws IOException {
        if (bindingResult.hasErrors()){
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
        productsService.save(product, file, personId, modelId);

        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String editProduct(@PathVariable("id") Long productId,
                              @ModelAttribute("product") @Valid Product newProduct,
                              @RequestParam("modelId") Long modelId,
                              BindingResult bindingResult,
                              Model model){
        if (bindingResult.hasErrors()){
            Product product = productsService.findById(productId);
            List<Image> images = productsService.imagesByProduct(product);

            model.addAttribute("images", images);

            return "admin/productEditPage";
        }

        productsService.update(productId, newProduct, modelId);

        return "redirect:/admin/editProduct/" + productId;
    }

    @PatchMapping("/{id}/addPicture")
    public String addPicture(@PathVariable("id") Long productId,
                             @RequestParam("file") MultipartFile file) throws IOException {
        productsService.addPicture(productId, file);
        return "redirect:/admin/editProduct/" + productId;
    }

    @PatchMapping("/{id}/setMainPicture")
    public String setMainPicture(@PathVariable("id") Long productId, @RequestParam("imageId") Long imageId){
        productsService.setMainPicture(imageId);

        return "redirect:/admin/editProduct/" + productId;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productsService.deleteProduct(id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/deletePicture")
    public String deletePicture(@PathVariable("id") Long productId, @RequestParam("imageId") Long imageId){
        productsService.deleteImageById(imageId);

        return "redirect:/admin/editProduct/" + productId;
    }
}
