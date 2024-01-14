package com.VANStore.version_alpha_1_0.util;

import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        Optional<Person> personFromDb = peopleService.findByEmail(person.getEmail());

        if (personFromDb.isPresent()){
            errors.rejectValue("email", "", "Человек с таким email уже существует");
        }
    }
}
