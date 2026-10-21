package kevinCHSE.GroceryStore.Spring.service;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kevinCHSE.GroceryStore.Spring.interfaces.invoiceDetailsServiceInterface;
import kevinCHSE.GroceryStore.Spring.repositories.invoiceDetailsRepository;




@Service 
public class invoiceDetailsService implements invoiceDetailsServiceInterface{

    private final invoiceDetailsRepository repository;

    public invoiceDetailsService(invoiceDetailsRepository repository) {
        this.repository = repository;
    }



    @Override
    @Transactional(readOnly=true)
    public int getTotal() {
        LocalDate today=LocalDate.now(ZoneId.of("America/Costa_Rica"));
        LocalDate startDate=  today.withDayOfMonth(1);
        System.out.println(startDate);
        System.out.println(startDate.plusMonths(1));
        return repository.getTotal(startDate, startDate.plusMonths(1));
    }

    

}
