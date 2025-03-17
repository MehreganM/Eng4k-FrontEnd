package com.backend;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Scanner;

@Service
public class SerialCommunicationService {

    private SerialPort serialPort;
    private OutputStream outputStream;
    private Scanner scanner;

    public SerialCommunicationService() {
        // Specify your Arduino Port (Change as needed)
        String portName = "/dev/ttyUSB0"; // Linux/macOS
        // String portName = "COM3"; // Windows

        serialPort = SerialPort.getCommPort(portName);
        serialPort.setBaudRate(9600); // Must match Arduino baud rate

        if (serialPort.openPort()) {
            System.out.println("✅ Serial Port Opened: " + portName);
            outputStream = serialPort.getOutputStream();
            scanner = new Scanner(serialPort.getInputStream());
        } else {
            System.out.println("❌ Failed to Open Serial Port: " + portName);
        }
    }

    /**
     * Send a command to Arduino
     */
    public void sendCommand(String command) {
        try {
            outputStream.write(command.getBytes());
            outputStream.flush();
            System.out.println("📤 Sent: " + command);
        } catch (Exception e) {
            System.out.println("❌ Error Sending Command: " + e.getMessage());
        }
    }

    /**
     * Read a response from Arduino
     */
    public String readResponse() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }

    public void closeConnection() {
        if (serialPort.isOpen()) {
            serialPort.closePort();
            System.out.println("🔌 Serial Port Closed");
        }
    }
}
