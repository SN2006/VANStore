package com.VANStore.version_alpha_1_0.controller;

import com.VANStore.version_alpha_1_0.models.CarBrand;
import com.VANStore.version_alpha_1_0.models.CarModel;
import com.VANStore.version_alpha_1_0.services.CarModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carModel")
public class CarModelController {

    private final CarModelsService carModelsService;

    @Autowired
    public CarModelController(CarModelsService carModelsService) {
        this.carModelsService = carModelsService;
    }

    @PostMapping("")
    public String createModel(@ModelAttribute("newModel")CarModel carModel,
                         @RequestParam("brandId") Long brandId){
        carModel.setName(carModel.getName().toUpperCase());
        System.out.println(carModel.getId());
        carModelsService.save(carModel, brandId);
        return "redirect:/owner";
    }

    @PatchMapping("/{id}")
    public String updateModel(@PathVariable("id") Long id, @ModelAttribute("editModel") CarModel carModel){
        carModelsService.update(id, carModel);
        return "redirect:/owner";
    }

    @DeleteMapping("/{id}")
    public String deleteModel(@PathVariable("id") Long id){
        carModelsService.deleteById(id);
        return "redirect:/owner";
    }
}
