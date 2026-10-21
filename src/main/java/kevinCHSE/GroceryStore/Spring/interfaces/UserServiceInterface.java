package kevinCHSE.GroceryStore.Spring.interfaces;

import java.util.List;

import kevinCHSE.GroceryStore.Spring.DTOs.UserDTO;
import kevinCHSE.GroceryStore.Spring.entities.Login.User;


public interface UserServiceInterface {
        List<UserDTO>getUsers();
        UserDTO save(User user);

}
