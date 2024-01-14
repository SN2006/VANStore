package com.VANStore.version_alpha_1_0.controller;

import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.security.PersonDetails;
import com.VANStore.version_alpha_1_0.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final OrdersService ordersService;

    @Autowired
    public MainController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("")
    public String index(){
        return "index";
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        person.setAdmin(person.getRole().equals("ROLE_ADMIN") || person.getRole().equals("ROLE_OWNER"));
        person.setOwner(person.getRole().equals("ROLE_OWNER"));
        model.addAttribute("person", person);
        model.addAttribute("orders", ordersService.findByBuyerId(person.getId()));
        return "newProfile";
    }

}
