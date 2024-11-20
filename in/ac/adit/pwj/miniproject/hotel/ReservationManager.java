package in.ac.adit.pwj.miniproject.hotel;

import java.io.*;
import java.util.*;

class ReservationManager {
    private List<Room> rooms;
    private Queue<Reservation> reservations;

    public ReservationManager() {
        rooms = new ArrayList<>();
        reservations = new LinkedList<>();
        loadReservations();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public synchronized void bookRoom(String roomNumber) throws BookingConflictException {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber) && room.isBooked()) {
                throw new BookingConflictException("Room already booked!");
            } else if (room.getRoomNumber().equals(roomNumber)) {
                room.book();
                reservations.add(new Reservation(roomNumber, new Date()));
                saveReservations();
                return;
            }
        }
        throw new BookingConflictException("Room not found!");
    }

    public synchronized void cancelBooking(String roomNumber) throws InvalidReservationException {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber) && room.isBooked()) {
                room.cancel();
                removeReservation(roomNumber);
                saveReservations();
                return;
            } else if (room.getRoomNumber().equals(roomNumber)) {
                throw new InvalidReservationException("Room is not booked!");
            }
        }
        throw new InvalidReservationException("Room not found!");
    }

    public synchronized List<String> checkAvailability() {
        List<String> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isBooked()) {
                availableRooms.add(room.getRoomNumber());
            }
        }
        return availableRooms;
    }

    private void saveReservations() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reservations.dat"))) {
            oos.writeObject(new ArrayList<>(reservations));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadReservations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservations.dat"))) {
            reservations.addAll((Collection<? extends Reservation>) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void removeReservation(String roomNumber) {
        reservations.removeIf(reservation -> reservation.getRoomNumber().equals(roomNumber));
    }

    class Reservation implements Serializable {
        private String roomNumber;
        private Date date;

        public Reservation(String roomNumber, Date date) {
            this.roomNumber = roomNumber;
            this.date = date;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public Date getDate() {
            return date;
        }
    }
}
