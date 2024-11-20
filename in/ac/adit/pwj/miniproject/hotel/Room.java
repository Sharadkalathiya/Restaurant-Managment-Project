package in.ac.adit.pwj.miniproject.hotel;


abstract class Room {
    protected String roomNumber;
    protected boolean isBooked;

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
        this.isBooked = false;
    }

    public abstract String getType();

    public String getRoomNumber() {
        return roomNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        isBooked = true;
    }

    public void cancel() {
        isBooked = false;
    }
}

class SingleRoom extends Room {
    public SingleRoom(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public String getType() {
        return "Single";
    }
}

class DoubleRoom extends Room {
    public DoubleRoom(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public String getType() {
        return "Double";
    }
}

class Suite extends Room {
    public Suite(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public String getType() {
        return "Suite";
    }
}
