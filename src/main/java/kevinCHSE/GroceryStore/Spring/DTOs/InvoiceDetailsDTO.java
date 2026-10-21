package kevinCHSE.GroceryStore.Spring.DTOs;

public class InvoiceDetailsDTO {
    private Integer productId;
    private Integer amount;
    

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
