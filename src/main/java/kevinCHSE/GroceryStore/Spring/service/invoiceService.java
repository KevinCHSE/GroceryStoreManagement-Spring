package kevinCHSE.GroceryStore.Spring.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kevinCHSE.GroceryStore.Spring.DTOs.InvoiceDTO;
import kevinCHSE.GroceryStore.Spring.DTOs.InvoiceDetailsDTO;
import kevinCHSE.GroceryStore.Spring.DTOs.PurchaseReport.PurchaseDTO;
import kevinCHSE.GroceryStore.Spring.DTOs.PurchaseReport.PurchasesItem;
import kevinCHSE.GroceryStore.Spring.DTOs.PurchaseReport.PurchasesQuery;
import kevinCHSE.GroceryStore.Spring.DTOs.PurchaseReport.PurchasesReportDTO;
import kevinCHSE.GroceryStore.Spring.entities.Client;
import kevinCHSE.GroceryStore.Spring.entities.Invoice;
import kevinCHSE.GroceryStore.Spring.entities.Products;
import kevinCHSE.GroceryStore.Spring.entities.invoiceDetails;
import kevinCHSE.GroceryStore.Spring.exeptions.BusinessRuleException;
import kevinCHSE.GroceryStore.Spring.exeptions.ResourceNotFoundException;
import kevinCHSE.GroceryStore.Spring.interfaces.invoiceServiceInterface;
import kevinCHSE.GroceryStore.Spring.repositories.InvoiceRepository;
import kevinCHSE.GroceryStore.Spring.repositories.clientRepository;
import kevinCHSE.GroceryStore.Spring.repositories.invoiceDetailsRepository;
import kevinCHSE.GroceryStore.Spring.repositories.productRepository;



@Service 
public class invoiceService implements invoiceServiceInterface{

    private final InvoiceRepository invoiceRepository;
    private final clientRepository clientRepository;
    private final productRepository productRepository;
    private final invoiceDetailsRepository detailsRepository;

    public invoiceService(clientRepository clientRepository, invoiceDetailsRepository detailsRepository, InvoiceRepository invoiceRepository, productRepository productRepository) {
        this.clientRepository = clientRepository;
        this.detailsRepository = detailsRepository;
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
    }

    



    @Override
    @Transactional(readOnly=true)
    public List<Invoice> invoiceList() {
        return (List<Invoice>) invoiceRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<Invoice> getInvoiceDetails(Integer id) {
        return invoiceRepository.findById(id);
    }

    @Override
    @Transactional
    public Invoice save(InvoiceDTO invoiceDTO) {
        Client client= clientRepository.findById(invoiceDTO.getClientId()).orElseThrow();
        if(client.isActive()){
            int invoiceTotal=0;

            Invoice invoice=new Invoice();
            invoice.setClient(client);
            invoice.setDate(LocalDate.now());
            invoice.setPayment(invoiceDTO.getPayment());
            

            invoiceRepository.save(invoice);
            for (InvoiceDetailsDTO item : invoiceDTO.getItems()) {
                Products product=productRepository.findById(item.getProductId()).orElseThrow(()->new ResourceNotFoundException("Cliente no encontrado"));
                if(product.isActive()){
                    invoiceDetails invoiceDetails=new invoiceDetails();
                    invoiceDetails.setInvoice(invoice);
                    invoiceDetails.setProduct(product);
                    invoiceDetails.setPrice( (product.getPrice()*item.getAmount()) );
                    invoiceDetails.setUnitPrice(product.getPrice());
                    invoiceDetails.setAmount(item.getAmount());
                    
                    //invoice's total
                    invoiceTotal+=invoiceDetails.getPrice();

                    if (product.getStock() < item.getAmount()) {
                        throw new BusinessRuleException("El producto: "+product.getName()+ " no tiene stock suficiente");
                    }
                    //amount of products
                    product.setStock(product.getStock()-item.getAmount());

                    detailsRepository.save(invoiceDetails);
                }
            }
        

            if (invoice.getPayment().equals("Contado")) {
                client.setTotalSpent(client.getTotalSpent()+invoiceTotal);
            }else{

                if(client.getCreditLimit()>invoiceTotal){
                    client.setUsedCredit(client.getUsedCredit()+invoiceTotal);
                    client.setTotalSpent(client.getTotalSpent()+invoiceTotal);
                }else{
                    throw new BusinessRuleException("El usuario: "+ client.getName()+" tiene un limite de credito de: "+client.getCreditLimit()+ "y esta compra sobrepasa ese limite" );
                }
            }
        
            return  invoice;
        }
        return null;
    }

    @Override 
    @Transactional 
    public PurchasesReportDTO getReport(PurchasesQuery query){
        List<Invoice> invoiceList = invoiceRepository.getReport(query.getIdClient(), query.getStartDate(), query.getEndDate());

        List<PurchaseDTO> purchasesList = new ArrayList<>();

        int total = 0;
        int cash = 0;
        int onCredit = 0;

        for (Invoice invoice : invoiceList) {
            List<invoiceDetails> details = detailsRepository.getDetailsByInvoice(invoice.getIdInvoice());
            List<PurchasesItem> purchasesItemList = new ArrayList<>();
            int totalFactura = 0;

            for (invoiceDetails item : details) {
                PurchasesItem newItem = new PurchasesItem();
                newItem.setItem(item.getProduct().getName());
                newItem.setAmount(item.getAmount());
                purchasesItemList.add(newItem);

                totalFactura += item.getPrice();
            }

            PurchaseDTO newPurchase = new PurchaseDTO();
            newPurchase.setDate(invoice.getDate());
            newPurchase.setItems(purchasesItemList);
            newPurchase.setPayment(invoice.getPayment());
            newPurchase.setTotal(totalFactura);
            purchasesList.add(newPurchase);

            total += totalFactura;
            if (invoice.getPayment().equals("Credito")) {
                onCredit += totalFactura;
            } else {
                cash += totalFactura;
            }
        }

        return new PurchasesReportDTO(purchasesList, total, cash, onCredit);
        }



}
