package kevinCHSE.GroceryStore.Spring.repositories.LoginRepository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import kevinCHSE.GroceryStore.Spring.entities.Login.Roles;




public interface RolesRepository extends CrudRepository<Roles, Integer>{

    Optional<Roles> findByRole(String role);

}
