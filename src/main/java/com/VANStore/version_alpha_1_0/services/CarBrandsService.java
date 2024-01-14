package com.VANStore.version_alpha_1_0.services;

import com.VANStore.version_alpha_1_0.models.CarBrand;
import com.VANStore.version_alpha_1_0.models.CarModel;
import com.VANStore.version_alpha_1_0.repositories.CarBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarBrandsService {

    private final CarBrandRepository carBrandRepository;

    @Autowired
    public CarBrandsService(CarBrandRepository carBrandRepository) {
        this.carBrandRepository = carBrandRepository;
    }

    @Transactional(readOnly = true)
    public List<CarBrand> findAll(){
        return carBrandRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CarBrand findById(Long id){
        return carBrandRepository.findById(id).orElse(null);
    }

    public List<CarModel> findCarModelsById(Long id){
        CarBrand carBrand = carBrandRepository.findById(id).orElse(null);

        if (carBrand == null) return new ArrayList<>();

        return carBrand.getCarModels();
    }

    @Transactional
    public void save(CarBrand carBrand){
        carBrandRepository.save(carBrand);
    }

    @Transactional
    public void update(Long id, CarBrand carBrand){
        carBrand.setId(id);
        carBrandRepository.save(carBrand);
    }

    @Transactional
    public void deleteById(Long id){
        carBrandRepository.deleteById(id);
    }
}
