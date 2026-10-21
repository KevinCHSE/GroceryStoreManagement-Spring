package kevinCHSE.GroceryStore.Spring.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import kevinCHSE.GroceryStore.Spring.entities.invoiceDetails;


public interface invoiceDetailsRepository extends CrudRepository<invoiceDetails, Integer>{

    @Query(value="""
            select sum(invoice_details.price) as total from invoice_details 
            right join invoice on invoice_id= invoice.id_invoice
            where invoice.date>= :startDate
            and invoice.date< :endDate
            """,nativeQuery=true)
    int getTotal(
        @Param("startDate") LocalDate starDate,
        @Param("endDate") LocalDate endDate
    );


    @Query(value="select * from invoice_details where invoice_id= :id", nativeQuery=true)
    List<invoiceDetails> getDetailsByInvoice(@Param("id") int invoice );

}
