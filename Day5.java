
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Part 1 answer:  5248
 * Part 2 answer:  4507
 */
public class Day5 {

    // Initial input arrays
    private static List<List<String>> instructionsString = new ArrayList<List<String>>();
    private static List<List<String>> updatesString = new ArrayList<List<String>>();

    // Input arrays parsed to ints
    private static List<List<Integer>> instructions = new ArrayList<List<Integer>>();
    private static List<List<Integer>> updates = new ArrayList<List<Integer>>();

    public static void main(String[] args) {
        // Part 1
        getInput("Day5");
        parseToInts(instructionsString, instructions);
        parseToInts(updatesString, updates);
        List<Integer> safeUpdates = applyInstructions(instructions, updates);
        int sum = (getMiddleNumberSum(safeUpdates));
        System.out.println("Part 1 answer: " + sum);

        // Part 2
        List<Integer> unSafeUpdates = getUnsafeIDs(updates, safeUpdates);
        List<Integer> unSafeUpdatesReordered = fixUnsafeUpdates(unSafeUpdates, instructions);
        int sum2 = getMiddleNumberSum(unSafeUpdatesReordered);
        System.out.println("Part 2 answer: " + sum2);
    }

    private static List<Integer> fixUnsafeUpdates(List<Integer> unSafeUpdates, List<List<Integer>> instructions) {
        List<Integer> unSafeUpdatesReordered = new ArrayList<Integer>();

        for (int i = 0; i < unSafeUpdates.size(); i++) {
            int updateID = unSafeUpdates.get(i);
            List<Integer> unSafeUpdate = updates.get(updateID);
            Boolean fixed = false;
            while (!fixed) {
                fixed = true; // Assume the update is fixed
                for (List<Integer> instruction : instructions) {
                    int number1 = instruction.get(0);
                    int number2 = instruction.get(1);
                    if (unSafeUpdate.contains(number1) && unSafeUpdate.contains(number2)) {
                        if (unSafeUpdate.indexOf(number1) > unSafeUpdate.indexOf(number2)) {
                            int index1 = unSafeUpdate.indexOf(number1);
                            int index2 = unSafeUpdate.indexOf(number2);
                            fixed = false; // If the first number is after the second number, the update is not fixed
                            unSafeUpdate.set(index1, number2);
                            unSafeUpdate.set(index2, number1);
                        }
                    }
                }
                // If the loop has made it to the end and fixed is still true, the update is fixed
            }
            unSafeUpdatesReordered.add(updateID);
        }

        return unSafeUpdatesReordered;
    }

    // Get the IDs of the updates that are not safe
    private static List<Integer> getUnsafeIDs(List<List<Integer>> updates, List<Integer> safeUpdates) {
        List<Integer> unSafeUpdates = new ArrayList<Integer>();

        for (int i = 0; i < updates.size(); i++) {
            if (!safeUpdates.contains(i)) {
                unSafeUpdates.add(i);
            }
        }

        return unSafeUpdates;
    }

    // Get the sum of the middle numbers of the safe updates
    private static int getMiddleNumberSum(List<Integer> updatesToSum) {
        int sum = 0;
        for (int updateID : updatesToSum) {
            List<Integer> updateToAdd = updates.get(updateID);
            sum += updateToAdd.get(updateToAdd.size() / 2);
        }
        return sum;
    }

    // Apply the instructions to the updates and return the safe updates
    private static List<Integer> applyInstructions(List<List<Integer>> instructions, List<List<Integer>> updates) {

        List<Integer> safeUpdates = new ArrayList<Integer>();

        for (int i = 0; i < updates.size(); i++) {
            Boolean safe = true; // Assume the update is safe
            // Check each instruction for each update
            for (List<Integer> instruction : instructions) {
                List<Integer> update = updates.get(i);
                int number1 = instruction.get(0);
                int number2 = instruction.get(1);
                // Check if the two numbers in the instruction are in the update
                if (update.contains(number1) && update.contains(number2)) {
                    if (update.indexOf(number1) > update.indexOf(number2)) {
                        safe = false; // If the first number is after the second number, the update is not safe
                    }
                }
            }
            if (safe) {
                safeUpdates.add(i);
            }
        }
        return safeUpdates;
    }

    // Parse a List of List<String> to a List of List<Integer>
    private static void parseToInts(List<List<String>> arraysOfNumberStrings, List<List<Integer>> arraysOfNumbers) {
        for (List<String> arrayOfNumberStrings : arraysOfNumberStrings) {
            List<Integer> numbersToInts = new ArrayList<Integer>();
            for (String numberString : arrayOfNumberStrings) {
                // System.out.println("Parsing to int: " + arrayOfNumberStrings[i]);
                numbersToInts.add(Integer.parseInt(numberString.trim()));
            }
            arraysOfNumbers.add(numbersToInts);
        }
    }

    // Read in the input file
    private static void getInput(String name) {
        // Read in file
        File file = new File("./Data Files/" + name + ".txt");
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);

            String inputLine = br.readLine(); // Read in first line
            while (inputLine != null && !inputLine.trim().isEmpty()) { // Read in instructions until empty line
                String[] instruction = inputLine.split("\\|");
                List<String> instructionList = Arrays.asList(instruction);
                instructionsString.add(instructionList);
                inputLine = br.readLine(); // Read in next line & repeat
            }
            inputLine = br.readLine(); // Discard the empty line
            while (inputLine != null) { // Read in updates until end of file
                String[] updateNumbers = inputLine.split(",");
                List<String> updateList = Arrays.asList(updateNumbers);
                updatesString.add(updateList);
                inputLine = br.readLine(); // Read in next line & repeat
            }
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

}