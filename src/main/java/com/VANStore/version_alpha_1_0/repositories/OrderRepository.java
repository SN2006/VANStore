package com.VANStore.version_alpha_1_0.repositories;

import com.VANStore.version_alpha_1_0.models.Order;
import com.VANStore.version_alpha_1_0.models.Person;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyer(Person buyer);
    List<Order> findBySeller(Person seller);
    List<Order> findByBuyer(Person buyer, Sort sort);
    List<Order> findBySeller(Person seller, Sort sort);

}
