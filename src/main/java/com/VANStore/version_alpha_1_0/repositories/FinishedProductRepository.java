package com.VANStore.version_alpha_1_0.repositories;

import com.VANStore.version_alpha_1_0.models.FinishedProduct;
import com.VANStore.version_alpha_1_0.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinishedProductRepository extends JpaRepository<FinishedProduct, Long> {

    List<FinishedProduct> findByCartOwner(Person cartOwner);

}
