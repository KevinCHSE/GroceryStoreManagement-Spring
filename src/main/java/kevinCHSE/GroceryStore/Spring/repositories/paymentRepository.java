package kevinCHSE.GroceryStore.Spring.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import kevinCHSE.GroceryStore.Spring.entities.Payment;


public interface paymentRepository extends CrudRepository<Payment, Integer>{

    @Query(value="Select * from payment where client_id= :clientId order by star_date desc", nativeQuery=true)
    List<Payment> listPaymentsByClient(@Param("clientId") String id_client);

}
