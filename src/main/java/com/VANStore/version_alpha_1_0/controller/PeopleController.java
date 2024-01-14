package com.VANStore.version_alpha_1_0.controller;

import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.security.PersonDetails;
import com.VANStore.version_alpha_1_0.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") Long id){
        peopleService.update(id, person);
//        PersonDetails personDetails = new PersonDetails(peopleService.findById(id));
//        Authentication auth = new UsernamePasswordAuthenticationToken(personDetails, null, personDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        RequestContextHolder.currentRequestAttributes().setAttribute("SPRING_SECURITY_CONTEXT", auth, RequestAttributes.SCOPE_SESSION);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person newPerson = personDetails.getPerson();
        newPerson.setFirstName(person.getFirstName());
        newPerson.setSurname(person.getSurname());
        newPerson.setMiddleName(person.getMiddleName());
        newPerson.setPhone(person.getPhone());
        return "redirect:/userProfile";
    }

    @PatchMapping("/setAdmin")
    public String setAdmin(@ModelAttribute("newAdmin")Person person){
        peopleService.setRole(person.getId(), "ROLE_ADMIN");
        return "redirect:/owner";
    }

    @PatchMapping("/setUser")
    public String setUser(@ModelAttribute("newUser")Person person){
        peopleService.setRole(person.getId(), "ROLE_USER");
        return "redirect:/owner";
    }

}
