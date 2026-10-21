package kevinCHSE.GroceryStore.Spring.interfaces;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import kevinCHSE.GroceryStore.Spring.entities.productImage;



public interface productImageServiceInterface {
    productImage saveImage(Integer id, MultipartFile imagFile);
    Optional<productImage>findImageById(Integer id);
}
