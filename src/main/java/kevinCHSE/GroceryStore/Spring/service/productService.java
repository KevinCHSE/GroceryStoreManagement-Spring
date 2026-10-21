package kevinCHSE.GroceryStore.Spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kevinCHSE.GroceryStore.Spring.entities.Products;
import kevinCHSE.GroceryStore.Spring.interfaces.productServiceInterface;
import kevinCHSE.GroceryStore.Spring.repositories.productRepository;


@Service
public class productService implements productServiceInterface {

    private final productRepository repository;

    public productService(productRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional(readOnly=true)
    public List<Products> productList() {
        return (List<Products>) repository.findAll();
    }

    @Override 
    @Transactional(readOnly=true)
    public int needStock() {
        return repository.needStock();
    } 


    @Override
    @Transactional
    public Products save(Products products) {
        return repository.save(products);
    }

    @Override
    @Transactional
    public Optional<Products> update(Products products, int id) {
        Optional<Products>optionalProduct=repository.findById(id);
        if(optionalProduct.isPresent()){
            Products newProduct=optionalProduct.get();
            newProduct.setName(products.getName());
            newProduct.setPrice(products.getPrice());
            newProduct.setStock(products.getStock());
            newProduct.setType(products.getType());
            newProduct.setActive(products.isActive());
            return Optional.of(repository.save(newProduct));
        }
        return optionalProduct;
    }

    @Override
    @Transactional
    public Optional<Products> delate(int id) {
        Optional<Products>optionalProduct=repository.findById(id);
        optionalProduct.ifPresent(product->{
            product.setActive(false);
        });
        return optionalProduct;
    }


    @Override
    @Transactional(readOnly=true)
    public Optional<Products> getProduct(Integer id) {
        return repository.findById(id);
    }
    

}
