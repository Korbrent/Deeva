package to.us.tcfc;

import to.us.tcfc.SoundHandler.WindowsSoundHandler;

public class Main {
    public static void main(String[] args) {
        WindowsSoundHandler handler = new WindowsSoundHandler();
        handler.test();
//        ArduinoThreadHandler handler = ArduinoThreadHandler.getInstance();
//        handler.run();
    }
}