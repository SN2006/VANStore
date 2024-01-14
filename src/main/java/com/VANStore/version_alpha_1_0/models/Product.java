package com.VANStore.version_alpha_1_0.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotEmpty(message = "Название товара не должно быть пустым!")
    @Size(min = 2, max = 200, message = "Название товара должно быть от 2 до 200 символов в длину!")
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Min(value = 1, message = "Стоимость товара должна быть больше 0!")
    @Column(name = "price")
    private Integer price;
    @Column(name = "preview_image_id")
    private Long previewImageId;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    private CarModel carModel;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "product")
    private List<Image> images = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    private List<FinishedProduct> finishedProducts = new ArrayList<>();

    public Product(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getPreviewImageId() {
        return previewImageId;
    }

    public void setPreviewImageId(Long previewImageId) {
        this.previewImageId = previewImageId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public List<FinishedProduct> getFinishedProducts() {
        return finishedProducts;
    }

    public void setFinishedProducts(List<FinishedProduct> finishedProducts) {
        this.finishedProducts = finishedProducts;
    }

    public void addImage(Image image){
        image.setProduct(this);
        images.add(image);
    }


}
