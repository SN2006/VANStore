package com.VANStore.version_alpha_1_0.controller;

import com.VANStore.version_alpha_1_0.models.CarBrand;
import com.VANStore.version_alpha_1_0.models.CarModel;
import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.services.CarBrandsService;
import com.VANStore.version_alpha_1_0.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owner")
public class OwnerController {

    private final PeopleService peopleService;
    private final CarBrandsService carBrandsService;

    @Autowired
    public OwnerController(PeopleService peopleService, CarBrandsService carBrandsService) {
        this.peopleService = peopleService;
        this.carBrandsService = carBrandsService;
    }

    @GetMapping("")
    public String ownerPanel(Model model,
                             @ModelAttribute("newAdmin")Person newAdmin,
                             @ModelAttribute("newUser") Person newUser,
                             @ModelAttribute("newBrand")CarBrand newCarBrand,
                             @ModelAttribute("newModel")CarModel newModel,
                             @ModelAttribute("selectedBrand") CarBrand selectedBrand,
                             @ModelAttribute("editBrand")CarBrand editCarBrand,
                             @ModelAttribute("editModel")CarModel editModel){
        model.addAttribute("users", peopleService.findUsers());
        model.addAttribute("admins", peopleService.findAdmins());
        model.addAttribute("brands", carBrandsService.findAll());
        return "owner/newOwnerPanel";
    }

}
