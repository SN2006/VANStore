package com.VANStore.version_alpha_1_0.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Car_Brand")
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column
    @Size(min = 1, max = 200, message = "Название марки машины должно быть от 1 до 200 символов в длину!")
    private String name;

    @OneToMany(mappedBy = "carBrand")
    private List<CarModel> carModels = new ArrayList<>();

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

    public List<CarModel> getCarModels() {
        return carModels;
    }

    public void setCarModels(List<CarModel> carModels) {
        this.carModels = carModels;
    }

    public void addModel(CarModel carModel){
        carModel.setCarBrand(this);
        carModels.add(carModel);
    }
}
