package to.us.tcfc;
import com.fazecast.jSerialComm.SerialPort;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ArduinoThreadHandler implements Runnable {
    private static ArduinoThreadHandler instance;
    private SerialPort port;
    public static String portName = "COM4";

    private ArduinoThreadHandler() {
        // Empty constructor
        port = SerialPort.getCommPort(portName);
        port.setComPortParameters(9600, 8, 1, 0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
        if(port.openPort()) {
            System.out.println("Successfully opened port " + portName);
        } else {
            System.out.println("Failed to open port " + portName);
        }
    }

    public static ArduinoThreadHandler getInstance() {
        if (instance == null) {
            instance = new ArduinoThreadHandler();
        }
        return instance;
    }

    @Override
    public void run() {
        char breakChar = '|';
        // Each input line is formatted as [A0|A1|A2|A3|A4]
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

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte readWithWait(){
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

}
