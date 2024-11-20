package in.ac.adit.pwj.miniproject.hotel;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HotelReservationSystem {
    private ReservationManager manager;
    private ExecutorService executor;

    public HotelReservationSystem() {
        manager = new ReservationManager();
        manager.addRoom(new SingleRoom("101"));
        manager.addRoom(new DoubleRoom("102"));
        manager.addRoom(new Suite("103"));

        executor = Executors.newFixedThreadPool(10);

        JFrame frame = new JFrame("Hotel Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Room Number");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField roomNumberText = new JTextField(20);
        roomNumberText.setBounds(150, 20, 165, 25);
        panel.add(roomNumberText);

        JButton bookButton = new JButton("Book Room");
        bookButton.setBounds(10, 80, 150, 25);
        panel.add(bookButton);

        JButton cancelButton = new JButton("Cancel Booking");
        cancelButton.setBounds(10, 120, 150, 25);
        panel.add(cancelButton);

        JButton checkButton = new JButton("Check Availability");
        checkButton.setBounds(10, 160, 150, 25);
        panel.add(checkButton);

        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(150, 80, 200, 100);
        panel.add(resultArea);

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String roomNumber = roomNumberText.getText();
                executor.submit(() -> {
                    try {
                        manager.bookRoom(roomNumber);
                        resultArea.setText("Room " + roomNumber + " booked successfully.");
                    } catch (BookingConflictException ex) {
                        resultArea.setText(ex.getMessage());
                    }
                });
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String roomNumber = roomNumberText.getText();
                executor.submit(() -> {
                    try {
                        manager.cancelBooking(roomNumber);
                        resultArea.setText("Booking for room " + roomNumber + " canceled.");
                    } catch (InvalidReservationException ex) {
                        resultArea.setText(ex.getMessage());
                    }
                });
            }
        });

        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executor.submit(() -> {
                    List<String> availableRooms = manager.checkAvailability();
                    resultArea.setText("Available Rooms: " + String.join(", ", availableRooms));
                });
            }
        });
    }

    public static void main(String[] args) {
        new HotelReservationSystem();
    }
}
