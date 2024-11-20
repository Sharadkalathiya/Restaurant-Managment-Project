package in.ac.adit.pwj.miniproject.restaurant;

class OrderConflictException extends Exception {
    public OrderConflictException(String message) {
        super(message);
    }
}

class InvalidOrderException extends Exception {
    public InvalidOrderException(String message) {
        super(message);
    }
}
