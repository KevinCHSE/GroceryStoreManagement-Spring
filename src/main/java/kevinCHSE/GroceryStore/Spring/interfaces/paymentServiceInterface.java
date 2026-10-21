package kevinCHSE.GroceryStore.Spring.interfaces;


import java.util.List;

import kevinCHSE.GroceryStore.Spring.DTOs.paymentQuery;
import kevinCHSE.GroceryStore.Spring.entities.Payment;


public interface paymentServiceInterface {

    List<Payment>paymentListByID(String id);
    Payment savePayment(paymentQuery query);


}
