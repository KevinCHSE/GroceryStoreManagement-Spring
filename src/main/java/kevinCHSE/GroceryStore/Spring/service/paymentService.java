package kevinCHSE.GroceryStore.Spring.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kevinCHSE.GroceryStore.Spring.DTOs.paymentQuery;
import kevinCHSE.GroceryStore.Spring.entities.Client;
import kevinCHSE.GroceryStore.Spring.entities.Payment;
import kevinCHSE.GroceryStore.Spring.exeptions.BusinessRuleException;
import kevinCHSE.GroceryStore.Spring.interfaces.paymentServiceInterface;
import kevinCHSE.GroceryStore.Spring.repositories.clientRepository;
import kevinCHSE.GroceryStore.Spring.repositories.paymentRepository;


;

@Service 
public class paymentService implements paymentServiceInterface {

    private final paymentRepository paymentRepository;
    private final clientRepository clientRepository;

    public paymentService(clientRepository clientRepository, paymentRepository paymentRepository) {
        this.clientRepository = clientRepository;
        this.paymentRepository = paymentRepository;
    }
    


    @Override
    @Transactional(readOnly = true)
    public List<Payment> paymentListByID(String id) {
        Optional<Client>optionalClient= clientRepository.findById(id);
        if(optionalClient.isEmpty()){
            return null;
        }

        return paymentRepository.listPaymentsByClient(id);
    }


    @Override
    @Transactional 
    public Payment savePayment(paymentQuery query) {
        Client client= clientRepository.findById(query.getClientId()).orElseThrow();

        if (query.getAmount()<=0 || query.getAmount()>client.getUsedCredit()) {
            throw new BusinessRuleException("El monto es inválido o supera lo que debe el cliente");
        }

        Payment payment=new Payment();
        payment.setClient(client);
        payment.setStarDate(LocalDate.parse(query.getStartDate()));
        payment.setEndDate(LocalDate.parse(query.getEndDate()));
        payment.setPaymentDate(LocalDate.now());
        payment.setAmount(query.getAmount());
        payment.setPriviuosBalance(client.getUsedCredit());
        payment.setNewBalance(client.getUsedCredit()-query.getAmount());

        int result=client.getUsedCredit()-query.getAmount();
        client.setUsedCredit(result);

        return paymentRepository.save(payment);
    }
    



     
    


}
