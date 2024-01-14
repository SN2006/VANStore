package com.VANStore.version_alpha_1_0.services;

import com.VANStore.version_alpha_1_0.models.FinishedProduct;
import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.models.Product;
import com.VANStore.version_alpha_1_0.repositories.FinishedProductRepository;
import com.VANStore.version_alpha_1_0.repositories.PeopleRepository;
import com.VANStore.version_alpha_1_0.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FinishedProductsService {

    private final FinishedProductRepository finishedProductRepository;
    private final ProductRepository productRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public FinishedProductsService(FinishedProductRepository finishedProductRepository, ProductRepository productRepository, PeopleRepository peopleRepository) {
        this.finishedProductRepository = finishedProductRepository;
        this.productRepository = productRepository;
        this.peopleRepository = peopleRepository;
    }

    @Transactional(readOnly = true)
    public List<FinishedProduct> findByCartOwner(Person cartOwner){
        return finishedProductRepository.findByCartOwner(cartOwner);
    }

    @Transactional(readOnly = true)
    public int totalPriceByCartOwner(Person cartOwner){
        List<FinishedProduct> finishedProducts = finishedProductRepository.findByCartOwner(cartOwner);
        int totalPrice = 0;

        for (FinishedProduct finishedProduct : finishedProducts){
            totalPrice += finishedProduct.getCount() * finishedProduct.getProduct().getPrice();
        }

        return totalPrice;
    }

    @Transactional
    public void createFinishedProduct(Long productId, Long buyerId, int count){
        FinishedProduct finishedProduct = new FinishedProduct(count);
        Product product = productRepository.findById(productId).orElse(null);
        Person buyer = peopleRepository.findById(buyerId).orElse(null);

        if (product == null || buyer == null) return;

        finishedProduct.setProduct(product);
        finishedProduct.setCartOwner(buyer);

        finishedProductRepository.save(finishedProduct);
    }

    @Transactional
    public void editCount(Long finishedProductId, int count){
        FinishedProduct finishedProduct = finishedProductRepository.findById(finishedProductId).orElse(null);

        if (finishedProduct == null) return;

        finishedProduct.setCount(count);
    }

    @Transactional
    public void deleteById(Long id){
        finishedProductRepository.deleteById(id);
    }

}
