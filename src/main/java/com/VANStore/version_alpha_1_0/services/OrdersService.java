package com.VANStore.version_alpha_1_0.services;

import com.VANStore.version_alpha_1_0.models.FinishedProduct;
import com.VANStore.version_alpha_1_0.models.Order;
import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.repositories.FinishedProductRepository;
import com.VANStore.version_alpha_1_0.repositories.OrderRepository;
import com.VANStore.version_alpha_1_0.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersService {

    private final OrderRepository orderRepository;
    private final PeopleRepository peopleRepository;
    private final FinishedProductRepository finishedProductRepository;

    @Autowired
    public OrdersService(OrderRepository orderRepository, PeopleRepository peopleRepository, FinishedProductRepository finishedProductRepository) {
        this.orderRepository = orderRepository;
        this.peopleRepository = peopleRepository;
        this.finishedProductRepository = finishedProductRepository;
    }

    @Transactional(readOnly = true)
    public Order findById(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Order> findBySellerId(Long sellerId){
        Person seller = peopleRepository.findById(sellerId).orElse(null);
        if (seller == null) return new ArrayList<>();

        return orderRepository.findBySeller(seller, Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional(readOnly = true)
    public List<Order> findByBuyerId(Long buyerId){
        Person buyer = peopleRepository.findById(buyerId).orElse(null);
        if (buyer == null) return new ArrayList<>();

        return orderRepository.findByBuyer(buyer, Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public void createOrder(Long buyerId){
        Person buyer = peopleRepository.findById(buyerId).orElse(null);
        Map<Long, Order> orderBySellerId = new HashMap<>();
        if(buyer == null) return;

        for (FinishedProduct product : buyer.getCart()){
            Person seller = product.getProduct().getPerson();
            if (!orderBySellerId.containsKey(seller.getId())){
                Order order = new Order("Заказ обрабатывается", buyer, seller);
                order.setTotalCount(product.getCount() * product.getProduct().getPrice());
                Order orderByDB = orderRepository.save(order);
                orderBySellerId.put(seller.getId(), orderByDB);

            }else {
                orderBySellerId.get(seller.getId()).addPrice(product.getCount() * product.getProduct().getPrice());
            }

            product.setOrder(orderBySellerId.get(product.getProduct().getPerson().getId()));
            product.setCartOwner(null);
        }
    }

    @Transactional
    public void setStatus(Long orderId, String status){
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) order.setStatus(status);
    }

    @Transactional
    public void deleteById(Long orderId){
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return;
        finishedProductRepository.deleteAll(order.getProducts());

        orderRepository.delete(order);
    }
}
