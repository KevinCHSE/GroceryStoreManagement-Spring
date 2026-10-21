package kevinCHSE.GroceryStore.Spring.DTOs;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class InvoiceDTO {

    @NotNull   
    private String clientId;
    @NotNull   
    private String payment;
    private List<InvoiceDetailsDTO>items;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<InvoiceDetailsDTO> getItems() {
        return items;
    }
}
