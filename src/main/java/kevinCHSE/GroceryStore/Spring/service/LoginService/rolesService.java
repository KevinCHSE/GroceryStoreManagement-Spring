package kevinCHSE.GroceryStore.Spring.service.LoginService;

import java.util.List;

import org.springframework.stereotype.Service;

import kevinCHSE.GroceryStore.Spring.entities.Login.Roles;
import kevinCHSE.GroceryStore.Spring.interfaces.roleServiceInterface;
import kevinCHSE.GroceryStore.Spring.repositories.LoginRepository.RolesRepository;


@Service 
public class rolesService implements roleServiceInterface {

    private final RolesRepository rolesRepository;

    public rolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<Roles> getUsers() {
        return (List<Roles>) rolesRepository.findAll();
    }

    @Override
    public Roles save(Roles roles) {
        return rolesRepository.save(roles);
    }

}
