package com.VANStore.version_alpha_1_0.controller;

import com.VANStore.version_alpha_1_0.models.Order;
import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.security.PersonDetails;
import com.VANStore.version_alpha_1_0.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrdersService ordersService;

    @Autowired
    public OrderController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/{id}")
    public String orderPage(@PathVariable("id") Long orderId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();

        Order order = ordersService.findById(orderId);
        if (order == null){
            return "redirect:/";
        }
        if (!order.containPerson(person)){
            return "redirect:/userProfile";
        }
        model.addAttribute("isSeller", order.getSeller().equals(person));
        model.addAttribute("order", order);
        model.addAttribute("products", order.getProducts());
        return "order";
    }

    @PatchMapping("/setStatus/{id}")
    public String setStatus(@PathVariable("id") Long orderId,
                            @RequestParam("status") String status){
        ordersService.setStatus(orderId, status);
        return "redirect:/orders/" + orderId;
    }

}
