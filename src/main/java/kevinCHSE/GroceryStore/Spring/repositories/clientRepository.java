package kevinCHSE.GroceryStore.Spring.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import kevinCHSE.GroceryStore.Spring.entities.Client;


public interface  clientRepository extends  CrudRepository<Client, String>{
    
    @Query(value="select count(id) from client where active=true", nativeQuery=true)
    public int getClientCount();   
}
