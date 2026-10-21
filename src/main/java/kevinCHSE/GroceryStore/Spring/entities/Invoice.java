package kevinCHSE.GroceryStore.Spring.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity  
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInvoice;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String payment ; // "contado" o "fiado"

    public Invoice() {
    }

    public Invoice(Client client, LocalDate date , String formaPago, Integer idInvoice) {
        this.client = client;
        this.date = date;
        this.payment = formaPago;
        this.idInvoice = idInvoice;
    }
    
    public Integer getIdInvoice() {
        return idInvoice;
    }
    public void setIdInvoice(Integer idInvoice) {
        this.idInvoice = idInvoice;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date ) {
        this.date = date;
    }
    public String getPayment() {
        return payment;
    }
    public void setPayment(String formaPago) {
        this.payment = formaPago;
    }


    
    
    
    

}