package com.VANStore.version_alpha_1_0.services;

import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findUsers(){
        return peopleRepository.findByRole("ROLE_USER");
    }

    public List<Person> findAdmins(){
        return peopleRepository.findByRole("ROLE_ADMIN");
    }

    public Optional<Person> findByEmail(String email){
        return peopleRepository.findByEmail(email);
    }

    public Person findById(Long id){
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Long id, Person updatedPerson){
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        if (optionalPerson.isEmpty()){
            return;
        }
        Person person = optionalPerson.get();
        person.setFirstName(updatedPerson.getFirstName());
        person.setSurname(updatedPerson.getSurname());
        person.setMiddleName(updatedPerson.getMiddleName());
        person.setPhone(updatedPerson.getPhone());
        peopleRepository.save(person);
    }

    @Transactional
    public void setRole(Long id, String role){
        Person person = peopleRepository.findById(id).orElse(null);
        if (person == null) return;
        person.setRole(role);
    }
}
