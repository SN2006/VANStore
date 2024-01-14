package com.VANStore.version_alpha_1_0.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов в длину")
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "Фамилия не должно быть пустым")
    @Size(min = 2, max = 100, message = "Фамилия должно быть от 2 до 100 символов в длину")
    @Column(name = "surname")
    private String surname;
    @NotEmpty(message = "Отчество не должно быть пустым")
    @Size(min = 2, max = 100, message = "Отчество должно быть от 2 до 100 символов в длину")
    @Column(name = "middle_name")
    private String middleName;
    @NotEmpty(message = "Email не должен быть пустым")
    @Email(message = "Email должен быть валидный")
    @Column(name = "email")
    private String email;
    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(min = 2, max = 100, message = "Пароль должен быть от 2 до 100 символов в длину")
    @Column(name = "password")
    private String password;
    @NotEmpty(message = "Телефон не должен быть пустым")
    @Column(name = "phone")
    private String phone;
    @Column(name = "role")
    private String role;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
    @OneToMany(mappedBy = "cartOwner")
    private List<FinishedProduct> cart;
    @OneToMany(mappedBy = "buyer")
    private List<Order> ordersToBuy;
    @OneToMany(mappedBy = "seller")
    private List<Order> ordersToSell;
    @Transient
    private boolean admin;
    @Transient
    private boolean owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<FinishedProduct> getCart() {
        return cart;
    }

    public void setCart(List<FinishedProduct> cart) {
        this.cart = cart;
    }

    public List<Order> getOrdersToBuy() {
        return ordersToBuy;
    }

    public void setOrdersToBuy(List<Order> ordersToBuy) {
        this.ordersToBuy = ordersToBuy;
    }

    public List<Order> getOrdersToSell() {
        return ordersToSell;
    }

    public void setOrdersToSell(List<Order> ordersToSell) {
        this.ordersToSell = ordersToSell;
    }

    public void addProduct(Product product){
        products.add(product);
        product.setPerson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return surname + ' ' +
                firstName + ' ' +
                middleName;
    }
}
