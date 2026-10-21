package kevinCHSE.GroceryStore.Spring.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import kevinCHSE.GroceryStore.Spring.entities.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {

    @Query(value = """
        SELECT * FROM invoice
        WHERE client_id = :clientId
        AND date BETWEEN :startDate AND :endDate order by date desc
        """, nativeQuery = true)
    List<Invoice> getReport(
        @Param("clientId") String clientId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}
