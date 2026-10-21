package kevinCHSE.GroceryStore.Spring.interfaces;

import java.util.List;
import java.util.Optional;

import kevinCHSE.GroceryStore.Spring.entities.Products;

public interface productServiceInterface {
    List<Products> productList();
    int needStock();
    Optional<Products> getProduct(Integer id);
    Products save(Products products);
    Optional<Products>update(Products products, int id );
    Optional<Products>delate(int id );
}
