package kevinCHSE.GroceryStore.Spring.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kevinCHSE.GroceryStore.Spring.DTOs.InvoiceDTO;
import kevinCHSE.GroceryStore.Spring.DTOs.PurchaseReport.PurchasesQuery;
import kevinCHSE.GroceryStore.Spring.interfaces.invoiceServiceInterface;
import kevinCHSE.GroceryStore.Spring.service.invoiceService;


@RestController
@RequestMapping("/apiInvoice")

public class InvoiceController {

    private final invoiceServiceInterface service;

    public InvoiceController(invoiceService service) {
        this.service = service;
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/saveInvoice")
    public ResponseEntity<?> save(@Valid @RequestBody InvoiceDTO invoiceDTO, BindingResult result) {
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.save(invoiceDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getReport")
    public ResponseEntity<?> getReport(@Valid @ModelAttribute PurchasesQuery query, BindingResult result) {
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.ok(service.getReport(query));
    }
    




    private ResponseEntity<?> validation(BindingResult result) {
        Map<String,String> errors=new HashMap<>();

        result.getFieldErrors().forEach(error->{
            errors.put(error.getField(), "El campo "+error.getField()+" "+error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }


    
}
