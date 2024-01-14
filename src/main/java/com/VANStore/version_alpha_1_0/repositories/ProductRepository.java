package com.VANStore.version_alpha_1_0.repositories;

import com.VANStore.version_alpha_1_0.models.CarModel;
import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.models.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPerson(Person person);
    List<Product> findByCarModel(CarModel carModel);
    List<Product> findByCarModel(CarModel carModel, Pageable pageable);
    List<Product> findByNameContainingIgnoreCase(String search, Pageable pageable);
    List<Product> findByNameContainingIgnoreCaseAndCarModel(String search, CarModel carModel, Pageable pageable);
}
