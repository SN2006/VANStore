package com.VANStore.version_alpha_1_0.controller;

import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.security.PersonDetails;
import com.VANStore.version_alpha_1_0.services.FinishedProductsService;
import com.VANStore.version_alpha_1_0.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final FinishedProductsService finishedProductsService;
    private final OrdersService ordersService;

    @Autowired
    public CartController(FinishedProductsService finishedProductsService, OrdersService ordersService) {
        this.finishedProductsService = finishedProductsService;
        this.ordersService = ordersService;
    }

    @GetMapping("")
    public String cart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();

        model.addAttribute("products", finishedProductsService.findByCartOwner(person));
        model.addAttribute("totalPrice", finishedProductsService.totalPriceByCartOwner(person));
        model.addAttribute("buyerId", person.getId());

        return "cart";
    }

    @PostMapping("")
    public String addProductToCart(@RequestParam("buyerId") Long buyerId,
                                   @RequestParam("productId") Long productId,
                                   @RequestParam("count") int count){
        finishedProductsService.createFinishedProduct(productId, buyerId, count);
        return "redirect:/cart";
    }

    @PostMapping("/createOrder")
    public String createOrder(@RequestParam("buyerId") Long buyerId){
        ordersService.createOrder(buyerId);
        return "redirect:/userProfile";
    }

    @PatchMapping("/editCount/{id}")
    public String editCount(@PathVariable("id") Long finishedProductId,
                            @RequestParam("count") int count){
        finishedProductsService.editCount(finishedProductId, count);
        return "redirect:/cart";
    }

    @DeleteMapping("/{id}")
    public String removeFinishedProduct(@PathVariable("id") Long id){
        finishedProductsService.deleteById(id);
        return "redirect:/cart";
    }

    @DeleteMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") Long orderId){
        ordersService.deleteById(orderId);
        return "redirect:/userProfile";
    }
}
