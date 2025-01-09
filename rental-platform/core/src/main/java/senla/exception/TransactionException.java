package senla.exception;

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(Throwable cause) {
        super(ExceptionEnum.TRANSACTION_ERROR.getMessage(), cause);
    }
}
