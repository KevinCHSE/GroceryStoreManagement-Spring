package kevinCHSE.GroceryStore.Spring.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity 
public class productImage {
    @Id
    @NotNull 
    private Integer productId; 

    @Column(nullable = false, length = 3 * 1024 * 1024)
    private byte[] data;

    @Column(nullable = false)
    private String contentType; 

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


}
