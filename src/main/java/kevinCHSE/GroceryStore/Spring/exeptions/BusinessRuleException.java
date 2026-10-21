package kevinCHSE.GroceryStore.Spring.exeptions;

public class BusinessRuleException extends RuntimeException{

    public BusinessRuleException(String message) {
        super(message);
    }

}
