package com.VANStore.version_alpha_1_0.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Oder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "total_count")
    private int totalCount;
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    private Person buyer;
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Person seller;
    @OneToMany(mappedBy = "order")
    private List<FinishedProduct> products = new ArrayList<>();

    public Order(){

    }

    public Order(String status, Person buyer, Person seller) {
        this.status = status;
        this.buyer = buyer;
        this.seller = seller;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Person getBuyer() {
        return buyer;
    }

    public void setBuyer(Person buyer) {
        this.buyer = buyer;
    }

    public Person getSeller() {
        return seller;
    }

    public void setSeller(Person seller) {
        this.seller = seller;
    }

    public void addPrice(int price){
        this.totalCount += price;
    }

    public List<FinishedProduct> getProducts() {
        return products;
    }

    public void setProducts(List<FinishedProduct> products) {
        this.products = products;
    }

    public boolean containPerson(Person person){
        return person.getId().equals(this.buyer.getId()) || person.getId().equals(this.seller.getId());
    }
}
