package kevinCHSE.GroceryStore.Spring.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kevinCHSE.GroceryStore.Spring.interfaces.invoiceDetailsServiceInterface;
import kevinCHSE.GroceryStore.Spring.service.invoiceDetailsService;




@RestController 
@RequestMapping("/apiInoviceDetails")

public class InvoiceDetailsController {

    private final invoiceDetailsServiceInterface service;

    public InvoiceDetailsController(invoiceDetailsService service) {
        this.service = service;
    }



    @GetMapping("/getTotal")
    @PreAuthorize("hasRole('ADMIN')")
    public int getTotal() {
        return service.getTotal();
    }
    

}
