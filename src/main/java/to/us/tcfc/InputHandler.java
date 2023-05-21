package to.us.tcfc;

public class InputHandler {
    private static InputHandler instance;

    private InputHandler() {
    }

    public static InputHandler getInstance() {
        if(instance == null) {
            instance = new InputHandler();
        }
        return instance;
    }

    public void handleInput(String line) {
        System.out.println(line);
        int[] intValues = getValues(line);
        double[] percentages = getPercentages(intValues);
    }

    /**
     * Gets the values from the input line.
     * @param inputLine The input line to get the values from.
     * @return The values from the input line.
     */
    private int[] getValues(String inputLine) {
        String[] inputValues = inputLine.split("\\|");
        int[] values = new int[inputValues.length];
        for (int i = 0; i < inputValues.length; i++) {
            values[i] = Integer.parseInt(inputValues[i]);
        }
        return values;
    }

    /**
     * Gets the percentages from the values.
     * @param values The values to get the percentages from.
     * @return The percentages from the values.
     */
    private double[] getPercentages(int[] values) {
        double[] percentages = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            percentages[i] = values[i] / 1023.0;
        }
        return percentages;
    }
}
