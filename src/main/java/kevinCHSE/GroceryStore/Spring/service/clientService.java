package kevinCHSE.GroceryStore.Spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kevinCHSE.GroceryStore.Spring.entities.Client;
import kevinCHSE.GroceryStore.Spring.interfaces.clientServiceInterface;
import kevinCHSE.GroceryStore.Spring.repositories.clientRepository;



@Service 
public class clientService implements clientServiceInterface{

    private final clientRepository repository;

    

    public clientService(clientRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Client> clientList() {
        return (List<Client>) repository.findAll();
    }

    @Override
    public Optional<Client> getClient(String id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Client save(Client client) {
        return repository.save(client);
    }

    @Override
    @Transactional
    public Optional<Client> update(Client Client, String id) {
        Optional<Client>optionalClient=repository.findById(id);
        if(optionalClient.isPresent()){
            Client newClient=optionalClient.get();
            newClient.setId(Client.getId());
            newClient.setName(Client.getName());
            newClient.setPhone(Client.getPhone());
            newClient.setUsedCredit(Client.getUsedCredit());
            newClient.setCreditLimit(Client.getCreditLimit());
            newClient.setActive(Client.isActive());
            newClient.setTotalSpent(Client.getTotalSpent());
            return Optional.of(repository.save(newClient));
        }
        return optionalClient;
    }

    @Override
    public int clientCount() {
       return repository.getClientCount();
    }

}
