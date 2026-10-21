package kevinCHSE.GroceryStore.Spring.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity 
public class invoiceDetails {

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idInvoiceDetails;

    @ManyToOne 
    @JoinColumn(name="invoice_id")
    private Invoice invoice; 

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Products product;
    @NotNull  
    private Integer price;
    @NotNull 
    private Integer unitPrice;
    @Min(1)
    private Integer amount;
    public invoiceDetails() {
    }

    public invoiceDetails(Integer amount, Integer idInvoiceDetails, Invoice invoice, Products product, Integer price, Integer unitPrice) {
        this.amount = amount;
        this.idInvoiceDetails = idInvoiceDetails;
        this.invoice = invoice;
        this.product = product;
        this.price = price;
        this.unitPrice = unitPrice;
    }

    public Integer getIdInvoiceDetails() {
        return idInvoiceDetails;
    }

    public void setIdInvoiceDetails(Integer idInvoiceDetails) {
        this.idInvoiceDetails = idInvoiceDetails;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    

    

}
