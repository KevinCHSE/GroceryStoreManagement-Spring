package kevinCHSE.GroceryStore.Spring.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Client {
    @Id
    @NotEmpty
    @NotBlank
    private String id;

    @NotEmpty
    @NotBlank
    private String name;

    @NotEmpty
    @NotBlank 
    private String phone; 
    @NotNull
    private Integer creditLimit=0;
    @NotNull
    private Integer usedCredit=0;
    @NotNull
    private Integer totalSpent=0;
    private boolean active=true;

    public Client() {
    }

    public Client(Integer Total, Integer balance, Integer creditLimit, String id, String name, String phone) {
        this.totalSpent = Total;
        this.usedCredit = balance;
        this.creditLimit = creditLimit;
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getUsedCredit() {
        return usedCredit;
    }

    public void setUsedCredit(Integer balance) {
        this.usedCredit = balance;
    }

    public Integer getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Integer Total) {
        this.totalSpent = Total;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    

    
}
   

   