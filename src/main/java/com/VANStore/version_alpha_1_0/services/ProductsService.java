package com.VANStore.version_alpha_1_0.services;

import com.VANStore.version_alpha_1_0.models.CarModel;
import com.VANStore.version_alpha_1_0.models.Image;
import com.VANStore.version_alpha_1_0.models.Person;
import com.VANStore.version_alpha_1_0.models.Product;
import com.VANStore.version_alpha_1_0.repositories.CarModelRepository;
import com.VANStore.version_alpha_1_0.repositories.ImageRepository;
import com.VANStore.version_alpha_1_0.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductsService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final PeopleService peopleService;
    private final CarModelRepository carModelRepository;

    @Autowired
    public ProductsService(ProductRepository productRepository, ImageRepository imageRepository, PeopleService peopleService, CarModelRepository carModelRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.peopleService = peopleService;
        this.carModelRepository = carModelRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> findAll(int page, int productPerPage){
        return productRepository.findAll(PageRequest.of(page, productPerPage)).getContent();
    }

    @Transactional(readOnly = true)
    public List<Product> findAll(int page, int productPerPage, String search){
        return productRepository.findByNameContainingIgnoreCase(search, PageRequest.of(page, productPerPage));
    }

    @Transactional(readOnly = true)
    public List<Product> findByPerson(Person person){
        return productRepository.findByPerson(person);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Image> imagesByProduct(Product product){
        return imageRepository.findByProduct(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findProductByCarModel(CarModel carModel){
        return productRepository.findByCarModel(carModel);
    }

    @Transactional(readOnly = true)
    public List<Product> findProductByCarModel(CarModel carModel, int page, int productPerPage){
        return productRepository.findByCarModel(carModel, PageRequest.of(page, productPerPage));
    }

    @Transactional(readOnly = true)
    public List<Product> findProductByCarModel(CarModel carModel, int page, int productPerPage, String search){
        return productRepository.findByNameContainingIgnoreCaseAndCarModel(search, carModel, PageRequest.of(page, productPerPage));
    }

    @Transactional
    public void save(Product product, MultipartFile file, Long personId, Long modelId) throws IOException{
        Image image;
        if (file.getSize() != 0){
            image = Image.toImageEntity(file);
            image.setPreviewImage(true);
            product.addImage(image);
        }
        Person person = peopleService.findById(personId);
        if (person == null) return;

        CarModel carModel = carModelRepository.findById(modelId).orElse(null);
        if (carModel == null) return;

        person.addProduct(product);
        product.setCarModel(carModel);
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        productRepository.save(product);
    }

    @Transactional
    public void addPicture(Long productId, MultipartFile file) throws IOException {
        Image image;
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return;

        if (file.getSize() != 0){
            image = Image.toImageEntity(file);
            product.addImage(image);
        }

        productRepository.save(product);
    }

    @Transactional
    public void setMainPicture(Long imageId){
        Image image = imageRepository.findById(imageId).orElse(null);
        if (image == null) return;
        Product product = image.getProduct();
        Image oldMainImage = imageRepository.findById(product.getPreviewImageId()).orElse(null);
        if (oldMainImage != null) oldMainImage.setPreviewImage(false);
        product.setPreviewImageId(imageId);
        image.setPreviewImage(true);

        imageRepository.save(image);
    }

    @Transactional
    public void update(Long id, Product product, Long modelId){
        Product productFromDb = productRepository.findById(id).orElse(null);
        if (productFromDb == null) return;

        productFromDb.setName(product.getName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setPrice(product.getPrice());

        CarModel carModel = carModelRepository.findById(modelId).orElse(null);
        if (carModel != null)  productFromDb.setCarModel(carModel);

        productRepository.save(productFromDb);
    }

    @Transactional
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    @Transactional
    public void deleteImageById(Long imageId){
        imageRepository.deleteById(imageId);
    }

//    private Image toImageEntity(MultipartFile file) throws IOException {
//        Image image = new Image();
//        image.setName(file.getName());
//        image.setOriginalFileName(file.getOriginalFilename());
//        image.setContentType(file.getContentType());
//        image.setSize(file.getSize());
//        image.setBytes(file.getBytes());
//        return image;
//    }
}
