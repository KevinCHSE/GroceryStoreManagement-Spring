package kevinCHSE.GroceryStore.Spring.DTOs.PurchaseReport;

import java.util.List;

public class PurchasesReportDTO {
 private List<PurchaseDTO> purchases;
    private int total;
    private int cash;
    private int onCredit;

    public PurchasesReportDTO(List<PurchaseDTO> purchases, int total, int cash, int onCredit) {
        this.purchases = purchases;
        this.total = total;
        this.cash = cash;
        this.onCredit = onCredit;
    }

    public List<PurchaseDTO> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseDTO> purchases) {
        this.purchases = purchases;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getOnCredit() {
        return onCredit;
    }

    public void setOnCredit(int onCredit) {
        this.onCredit = onCredit;
    }

}
