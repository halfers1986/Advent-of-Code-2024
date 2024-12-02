import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


/*
 * Part 1 answer:  2066446
 * Part 2 answer:  24931009
 */
public class Day1 {

    public final static Map<Integer, Integer> input = new TreeMap<Integer, Integer>() {}; // Auto sorts Keys

    public static void main(String[] args) {
        getInput("Day1");
        matchValues();
        int totalDistance = calculateDistance();
        System.out.println("Total distance: " + totalDistance);
        int totalSimilarity = calculateSimilarity();
        System.out.println("Total similarity: " + totalSimilarity);
    }

    /*
     * Iterate over the values associated with each key and compare.
     * If a value is lower than the value currently being compared to, switch the
     * values.
     * (Used in Part 1)
     */
    private static void matchValues() {
        for (Map.Entry<Integer, Integer> entry1 : input.entrySet()) {
            for (Map.Entry<Integer, Integer> entry2 : input.entrySet()) {
                // Keys are already sorted so ignore the entries for any keys that
                // are less than the current key, to avoid swapping the same values again
                if (entry2.getKey() > entry1.getKey()) {
                    if (entry2.getValue() < entry1.getValue()) {
                        // System.out.println(entry2.getValue() + " < " + entry1.getValue() + "; switching");
                        int temp = entry1.getValue();
                        entry1.setValue(entry2.getValue());
                        entry2.setValue(temp);
                    }
                }
            }

        }
    }

    private static int calculateSimilarity() {
        int totalSimilarity = 0;
        for (Map.Entry<Integer, Integer> entry1 : input.entrySet()) {
            int similarity = 0;
            int number = entry1.getKey();
            for (Map.Entry<Integer, Integer> entry2 : input.entrySet()) {
                if (number == entry2.getValue())
                    similarity++;
            }
            if (similarity > 0) {
                totalSimilarity += similarity*number;
            }
            
        }
        return totalSimilarity;
    }

    // Read in file and store values in a TreeMap
    private static void getInput(String name) {
        // Read in file
        File file = new File("./Data Files/" + name + ".txt");
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);

            String inputLine = br.readLine(); // Read in first line
            while (inputLine != null) {
                String fixedString = inputLine.replaceAll(" +", " "); // Replaces multiple spaces with a single space
                String[] splitLine = fixedString.split(" ");
                int int1 = Integer.parseInt(splitLine[0]);
                int int2 = Integer.parseInt(splitLine[1]);
                input.put(int1, int2);
                inputLine = br.readLine(); // Read in next line & repeat
            }
        } catch (NumberFormatException | IOException e) {
            if (e instanceof NumberFormatException) {
                System.out.println("Input cannot be parsed to a number");
                e.printStackTrace();
            } else {
                System.out.println("File not found");
                e.printStackTrace();
            }
        }
    }

    // Calculate the sum of the distances between each key-value (Used in Part 1)
    private static int calculateDistance() {
        int totalDistance = 0;
        for (Map.Entry<Integer, Integer> entry : input.entrySet()) {
            int distance = Math.abs(entry.getValue() - entry.getKey());
            totalDistance += distance;
        }
        return totalDistance;
    }

}