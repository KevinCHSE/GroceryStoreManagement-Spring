package kevinCHSE.GroceryStore.Spring.controller;

import java.time.Duration;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kevinCHSE.GroceryStore.Spring.interfaces.productImageServiceInterface;
import kevinCHSE.GroceryStore.Spring.service.productImageService;

@RestController 
@RequestMapping("/apiImage")
public class ProductImageController {

    private final productImageServiceInterface imageService;

    public ProductImageController(productImageService imageService) {
        this.imageService = imageService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')") 
    @PostMapping("/saveImage/{id}/image") 
    public ResponseEntity<?> saveImage(@PathVariable Integer id,
        @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(imageService.saveImage(id, file));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?>getImage(@PathVariable Integer id) {
        return imageService.findImageById(id)
        .map(img->ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(img.getContentType()))
        .cacheControl(CacheControl.maxAge(Duration.ofDays(7)))
        .body(img.getData())).orElse(ResponseEntity.notFound().build());
    }

}
