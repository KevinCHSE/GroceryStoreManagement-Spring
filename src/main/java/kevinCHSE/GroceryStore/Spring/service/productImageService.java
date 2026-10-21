package kevinCHSE.GroceryStore.Spring.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kevinCHSE.GroceryStore.Spring.entities.productImage;
import kevinCHSE.GroceryStore.Spring.exeptions.ResourceNotFoundException;
import kevinCHSE.GroceryStore.Spring.interfaces.productImageServiceInterface;
import kevinCHSE.GroceryStore.Spring.repositories.productImageRepository;
import kevinCHSE.GroceryStore.Spring.repositories.productRepository;



@Service 
public class productImageService implements productImageServiceInterface{

    private static final long MAX_SIZE= 2*1024*1024;
    private final productImageRepository imageRepository;
    private final productRepository productRepository;

    public productImageService(productImageRepository imageRepository, productRepository productRepository) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
    }



    @Override
    @Transactional
    public productImage saveImage(Integer id, MultipartFile imageFile) {
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("El producto no existe");
        }
        String type= imageFile.getContentType();
        if(type==null || !type.startsWith("image/")){
            throw new ResourceNotFoundException("El archivo tiene que ser una imagen");
        }
        if(imageFile.getSize()> MAX_SIZE){
            throw new ResourceNotFoundException("La imagen no puede pesar mas de 2MB");
        }
        productImage image= imageRepository.findById(id).orElseGet(productImage::new);
        image.setProductId(id);
        try {
            image.setData(imageFile.getBytes());
        } catch (IOException e) {
            throw new ResourceNotFoundException("ocurrio un error al guardar la imagen");
        }
        image.setContentType(type);
        return imageRepository.save(image);


    
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<productImage> findImageById(Integer id) {
        return imageRepository.findById(id);
    }

}
