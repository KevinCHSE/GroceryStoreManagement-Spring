package kevinCHSE.GroceryStore.Spring.repositories.LoginRepository;

import org.springframework.data.repository.CrudRepository;

import kevinCHSE.GroceryStore.Spring.entities.Login.User;


public interface  UserRepository extends CrudRepository<User, String>{

}
