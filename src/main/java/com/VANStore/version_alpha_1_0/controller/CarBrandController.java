package com.VANStore.version_alpha_1_0.controller;

import com.VANStore.version_alpha_1_0.models.CarBrand;
import com.VANStore.version_alpha_1_0.services.CarBrandsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carBrand")
public class CarBrandController {

    private final CarBrandsService carBrandsService;

    @Autowired
    public CarBrandController(CarBrandsService carBrandsService) {
        this.carBrandsService = carBrandsService;
    }

    @PostMapping("")
    public String createBrand(Model model,
                              @ModelAttribute("newBrand") @Valid CarBrand brand,
                              BindingResult bindingResult){
        brand.setName(brand.getName().toUpperCase());
        carBrandsService.save(brand);
        return "redirect:/owner";
    }

    @PatchMapping("/{id}")
    public String updateBrand(@PathVariable("id") Long brandId,
                              @ModelAttribute("editBrand") CarBrand brand){
        brand.setName(brand.getName().toUpperCase());
        carBrandsService.update(brandId, brand);
        return "redirect:/owner";
    }

    @DeleteMapping("/{id}")
    public String deleteBrand(@PathVariable("id") Long brandId){
        carBrandsService.deleteById(brandId);
        return "redirect:/owner";
    }

}
