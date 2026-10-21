package kevinCHSE.GroceryStore.Spring.DTOs.PurchaseReport;

import java.time.LocalDate;
import java.util.List;

public class PurchaseDTO {
    private LocalDate date;
    private List<PurchasesItem> items;
    private String payment;
    private int total;
 



    public PurchaseDTO() {
    }

    public PurchaseDTO(LocalDate date, List<PurchasesItem> items, String payment, int total) {
        this.date = date;
        this.items = items;
        this.payment = payment;
        this.total = total;
    }

    

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<PurchasesItem> getItems() {
        return items;
    }

    public void setItems(List<PurchasesItem> items) {
        this.items = items;
    }

    
    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }



}
