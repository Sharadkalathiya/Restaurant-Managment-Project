package in.ac.adit.pwj.miniproject.r2;

// Exception for Order Conflicts
class OrderConflictException extends Exception {
    public OrderConflictException(String message) {
        super(message);
    }
}

// Exception for Invalid Payment
class InvalidPaymentException extends Exception {
    public InvalidPaymentException(String message) {
        super(message);
    }
}
