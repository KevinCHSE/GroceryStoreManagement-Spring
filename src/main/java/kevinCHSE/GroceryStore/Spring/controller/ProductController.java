package kevinCHSE.GroceryStore.Spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kevinCHSE.GroceryStore.Spring.entities.Products;
import kevinCHSE.GroceryStore.Spring.interfaces.productServiceInterface;





@RestController
@RequestMapping("/apiProducts")
public class ProductController {

    public final productServiceInterface service;

    public ProductController(productServiceInterface service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/Products")
    public List<Products> findAllProducts(){
        List<Products>productsList=service.productList();
        return productsList;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/needStock")
    public int needStock(){
        return service.needStock();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/NewProduct")
    public ResponseEntity<?> saveProduct(@Valid @RequestBody Products product , BindingResult result) {
         if(result.hasFieldErrors()){
            return  validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("updateProduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @Valid @RequestBody Products products, BindingResult result) {
        if(result.hasFieldErrors()){
            return  validation(result);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.update(products, id));
        
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("getProduct/{id}")
    public Optional<Products> getProducts(@PathVariable int id) {
        return service.getProduct(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("updateStockProduct/{id}")
    public ResponseEntity<?> updateStockProduct(@PathVariable int id, @RequestBody int newStock) {
        Optional<Products>optionalProduct=service.getProduct(id);
        if(optionalProduct.isPresent()){
            int lastStock=optionalProduct.get().getStock();
            optionalProduct.get().setStock(lastStock+newStock);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.update(optionalProduct.get(),id));
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String,String> errors=new HashMap<>();

        result.getFieldErrors().forEach(error->{
            errors.put(error.getField(), "El campo "+error.getField()+" "+error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
