package com.VANStore.version_alpha_1_0.services;

import com.VANStore.version_alpha_1_0.models.CarBrand;
import com.VANStore.version_alpha_1_0.models.CarModel;
import com.VANStore.version_alpha_1_0.repositories.CarBrandRepository;
import com.VANStore.version_alpha_1_0.repositories.CarModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarModelsService {

    private final CarModelRepository carModelRepository;
    private final CarBrandRepository carBrandRepository;

    @Autowired
    public CarModelsService(CarModelRepository carModelRepository, CarBrandRepository carBrandRepository) {
        this.carModelRepository = carModelRepository;
        this.carBrandRepository = carBrandRepository;
    }

    @Transactional(readOnly = true)
    public List<CarModel> findAll(){
        return carModelRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CarModel findById(Long id){
        return carModelRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(CarModel carModel, Long id){
        CarBrand carBrand = carBrandRepository.findById(id).orElse(null);
        if (carBrand == null) return;

        carModel.setCarBrand(carBrand);
        carModelRepository.save(carModel);
    }

    @Transactional
    public void update(Long id, CarModel carModel){
        CarModel carModelFromDb = carModelRepository.findById(id).orElse(null);

        if (carModelFromDb == null) return;

        carModelFromDb.setName(carModel.getName());
    }

    @Transactional
    public void deleteById(Long id){
        carModelRepository.deleteById(id);
    }
}
