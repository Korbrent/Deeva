package to.us.tcfc;
import com.fazecast.jSerialComm.SerialPort;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Thread handler for the Arduino input.
 * Reads input from the Arduino and sends it to the input handler.
 *
 * @author Korbrent & meta1203
 * @version 1.0
 * @since 1.0
 */
public class ArduinoThreadHandler implements Runnable {
    private static ArduinoThreadHandler instance;
    private SerialPort port;
    public static String portName = "COM4";

    /**
     * Default constructor for the ArduinoThreadHandler.
     */
    private ArduinoThreadHandler() {
        port = SerialPort.getCommPort(portName);
        port.setComPortParameters(9600, 8, 1, 0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
        if (port.openPort()) {
            System.out.println("Successfully opened port " + portName);
        } else {
            System.out.println("Failed to open port " + portName);
        }
    }

    /**
     * Gets the instance of the ArduinoThreadHandler.
     * @return The instance of the ArduinoThreadHandler.
     */
    public static ArduinoThreadHandler getInstance() {
        if (instance == null) {
            instance = new ArduinoThreadHandler();
        }
        return instance;
    }

    /**
     * Runnable implementation for running the ArduinoThreadHandler.
     */
    @Override
    public void run() {
        try {
            while (true) {
                if (!port.isOpen()) {
                    // If the port is not open, try to open it
                    System.out.println("Port is not open, attempting to open it...");
                    if (port.openPort()) {
                        System.out.println("Successfully opened port " + portName);
                    } else {
                        System.out.println("Failed to open port " + portName);
                        return;
                    }
                }

                // Each input line is formatted as "A0|A1|A2|A3|A4"
                String inputLine = getLine();
                InputHandler.getInstance().handleInput(inputLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a single byte from the Arduino. Waits to read if there is no data available.
     * @return The byte read from the Arduino.
     */
    private byte readWithWait() {
        while (port.bytesAvailable() == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        byte[] inputBuffer = new byte[1];
        port.readBytes(inputBuffer, 1);
        return inputBuffer[0];
    }

    /**
     * Gets a line from the Arduino.
     * @return The line read from the Arduino.
     */
    private String getLine() {
        // Each input line is received as "[A0|A1|A2|A3|A4]"
        char currentChar = '?';
        // Wait for the start of the input line
        while (currentChar != '[') {
            currentChar = (char) readWithWait();
        }
        // Current char is now '['
        String inputLine = "";
        while (currentChar != ']') {
            currentChar = (char) readWithWait();
            inputLine += currentChar;
        }
        // Current char is now ']'
        System.out.println(inputLine);
        return inputLine.substring(0, inputLine.length() - 1); // Remove the trailing ']'
    }
}