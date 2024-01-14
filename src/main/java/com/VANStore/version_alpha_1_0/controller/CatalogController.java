package com.VANStore.version_alpha_1_0.controller;

import com.VANStore.version_alpha_1_0.models.CarModel;
import com.VANStore.version_alpha_1_0.models.Product;
import com.VANStore.version_alpha_1_0.services.CarBrandsService;
import com.VANStore.version_alpha_1_0.services.CarModelsService;
import com.VANStore.version_alpha_1_0.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    private final CarBrandsService carBrandsService;
    private final CarModelsService carModelsService;
    private final ProductsService productsService;

    @Autowired
    public CatalogController(CarBrandsService carBrandsService, CarModelsService carModelsService, ProductsService productsService) {
        this.carBrandsService = carBrandsService;
        this.carModelsService = carModelsService;
        this.productsService = productsService;
    }


    @GetMapping("/selectCategory")
    public String selectCategory(Model model){
        model.addAttribute("categories", carBrandsService.findAll());
        return "catalog/selectCategory";
    }

    @GetMapping("/selectCategory/{id}")
    public String selectSubcategory(@PathVariable("id") Long brandId,
            Model model){
        model.addAttribute("categories", carBrandsService.findCarModelsById(brandId));
        return "catalog/selectSubCategory";
    }

    @GetMapping("")
    public String catalog(@RequestParam(name = "modelId", required = false, defaultValue = "-1") Long modelId,
                          @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                          @RequestParam(name = "product_per_page", required = false, defaultValue = "30") int productPerPage,
                          @RequestParam(name = "search", required = false, defaultValue = "") String search,
                          Model model){
        List<Product> products = new ArrayList<>();
        if (modelId == -1){
            products = productsService.findAll(page, productPerPage, search);
        }else{
            CarModel carModel = carModelsService.findById(modelId);
            if (carModel != null) products = productsService.findProductByCarModel(carModel, page, productPerPage, search);
        }
        model.addAttribute("products", products);
        model.addAttribute("page", page);
        model.addAttribute("modelId", modelId);
        model.addAttribute("search", search);
        return "catalog/catalog";
    }

}
