package kevinCHSE.GroceryStore.Spring.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity 
public class Payment {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private Integer id;

    @ManyToOne 
    @JoinColumn(name="client_id") 
    private Client client;

    @NotNull 
    private LocalDate starDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private LocalDate paymentDate;
    @NotNull
    private Integer amount;
    @NotNull
    private Integer priviuosBalance;
    @NotNull
    private Integer newBalance;

    public Payment(Integer Amount, LocalDate EndDate, LocalDate PaymentDate, LocalDate StarDate, Client client, Integer id, Integer newBalance, Integer priviuosBalance) {
        this.amount = Amount;
        this.endDate = EndDate;
        this.paymentDate = PaymentDate;
        this.starDate = StarDate;
        this.client = client;
        this.id = id;
        this.newBalance = newBalance;
        this.priviuosBalance = priviuosBalance;
    }
    

    public Payment() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getStarDate() {
        return starDate;
    }

    public void setStarDate(LocalDate StarDate) {
        this.starDate = StarDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate EndDate) {
        this.endDate = EndDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate PaymentDate) {
        this.paymentDate = PaymentDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer Amount) {
        this.amount = Amount;
    }

    public Integer getPriviuosBalance() {
        return priviuosBalance;
    }

    public void setPriviuosBalance(Integer priviuosBalance) {
        this.priviuosBalance = priviuosBalance;
    }

    public Integer getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(Integer newBalance) {
        this.newBalance = newBalance;
    }


}
