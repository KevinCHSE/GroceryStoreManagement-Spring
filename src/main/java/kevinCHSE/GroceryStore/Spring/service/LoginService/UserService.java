package kevinCHSE.GroceryStore.Spring.service.LoginService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kevinCHSE.GroceryStore.Spring.DTOs.UserDTO;
import kevinCHSE.GroceryStore.Spring.entities.Login.Roles;
import kevinCHSE.GroceryStore.Spring.entities.Login.User;
import kevinCHSE.GroceryStore.Spring.interfaces.UserServiceInterface;
import kevinCHSE.GroceryStore.Spring.repositories.LoginRepository.RolesRepository;
import kevinCHSE.GroceryStore.Spring.repositories.LoginRepository.UserRepository;



@Service 
public class UserService implements UserServiceInterface{

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    

    

    public UserService(UserRepository userRepository, RolesRepository rolesRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User>users=(List<User>) userRepository.findAll();

        return  users.stream()
        .map(user-> new UserDTO(user))
        .toList();
    }

    @Override
    public UserDTO save(User user) {
        Optional<Roles>optionalRole= rolesRepository.findByRole("ROLE_USER");
        Set<Roles>roles= new HashSet<>();
        optionalRole.ifPresent(role->roles.add(role));

        if(user.isAdmin()==true){
            Optional<Roles>optionalRoleAdmin= rolesRepository.findByRole("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(role->roles.add(role));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return new UserDTO(user);
    }

}
