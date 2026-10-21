package kevinCHSE.GroceryStore.Spring.interfaces;

import java.util.List;

import kevinCHSE.GroceryStore.Spring.entities.Login.Roles;


public interface roleServiceInterface {
        List<Roles>getUsers();
        Roles save(Roles roles);
}
