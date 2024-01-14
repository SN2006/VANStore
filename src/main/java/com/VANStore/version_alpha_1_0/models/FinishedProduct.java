package com.VANStore.version_alpha_1_0.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Finished_Product")
public class FinishedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "count")
    private int count;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "cart_owner_id", referencedColumnName = "id")
    private Person cartOwner;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    public FinishedProduct(){

    }

    public FinishedProduct(int count){
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Person getCartOwner() {
        return cartOwner;
    }

    public void setCartOwner(Person cartOwner) {
        this.cartOwner = cartOwner;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinishedProduct that = (FinishedProduct) o;
        return count == that.count && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count);
    }
}
