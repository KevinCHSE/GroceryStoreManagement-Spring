package kevinCHSE.GroceryStore.Spring.interfaces;

import java.util.List;
import java.util.Optional;

import kevinCHSE.GroceryStore.Spring.DTOs.InvoiceDTO;
import kevinCHSE.GroceryStore.Spring.DTOs.PurchaseReport.PurchasesQuery;
import kevinCHSE.GroceryStore.Spring.DTOs.PurchaseReport.PurchasesReportDTO;
import kevinCHSE.GroceryStore.Spring.entities.Invoice;

public interface  invoiceServiceInterface {
    List<Invoice> invoiceList();
    Optional<Invoice> getInvoiceDetails(Integer id);
    Invoice save(InvoiceDTO invoiceDTO);
     public PurchasesReportDTO getReport(PurchasesQuery query);
}
