package in.ac.adit.pwj.miniproject.hotel;

class BookingConflictException extends Exception {
    public BookingConflictException(String message) {
        super(message);
    }
}

class InvalidReservationException extends Exception {
    public InvalidReservationException(String message) {
        super(message);
    }
}
