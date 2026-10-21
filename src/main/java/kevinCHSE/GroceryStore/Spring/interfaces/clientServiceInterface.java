package kevinCHSE.GroceryStore.Spring.interfaces;

import java.util.List;
import java.util.Optional;

import kevinCHSE.GroceryStore.Spring.entities.Client;


public interface clientServiceInterface {
    List<Client> clientList();
    Optional<Client> getClient(String id);
    Client save(Client client);
    Optional<Client>update(Client Client, String id);
    int clientCount();
}
