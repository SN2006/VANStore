package com.VANStore.version_alpha_1_0.repositories;

import com.VANStore.version_alpha_1_0.models.Image;
import com.VANStore.version_alpha_1_0.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProduct(Product product);
}
