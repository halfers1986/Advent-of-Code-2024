import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Part 1 answer:  5540634308362
 * Part 2 answer:  472290821152397
 */
public class Day7 {

    public static final Map<Long, List<Long>> EQMAP = new HashMap<Long, List<Long>>();

    public static void main(String[] args) {
        getInput("Day7");
        // Part 1
        long total = 0;
        String[] operatorsPart1 = new String[] { "+", "*" };
        for (Map.Entry<Long, List<Long>> entry : EQMAP.entrySet()) {
            if (evaluateEquation(entry.getKey(), entry.getValue(), operatorsPart1)) {
                total += entry.getKey();
            }
        }
        System.out.println("Answer to Part 1: " + total);

        // Part 2
        long total2 = 0;
        String[] operatorsPart2 = new String[] { "+", "*", "|" };
        for (Map.Entry<Long, List<Long>> entry : EQMAP.entrySet()) {
            if (evaluateEquation(entry.getKey(), entry.getValue(), operatorsPart2)) {
                total2 += entry.getKey();
            }
        }
        System.out.println("Answer to Part 2: " + total2);
    }

    private static Boolean evaluateEquation(Long target, List<Long> values, String[] operators) {

        // Find all permutations of operators for length of the values array
        List<String> allOperatorPermutations = findAllOperatorPermutations(operators, values.size() - 1);

        // For each permutation, evaluate the expression
        for (String p : allOperatorPermutations) {
            long expressionValue = values.get(0);
            List<String> permutation = new ArrayList<>(Arrays.asList(p.split("")));

            for (int j = 0; j < permutation.size(); j++) {
                if (permutation.get(j).equals("+")) {
                    expressionValue += values.get(j + 1);
                } else if (permutation.get(j).equals("*")) {
                    expressionValue *= values.get(j + 1);
                } else if (permutation.get(j).equals("|")) {
                    expressionValue = Long.parseLong(Long.toString(expressionValue) + Long.toString(values.get(j + 1)));
                }
            }
            if (expressionValue == target) {
                return true;
            }
        }
        return false;
    }

    /*
     * Recursion method that will find all permutations of X operators (passed as an
     * array of operator types) for given length (i) of array of operators.
     */
    private static List<String> findAllOperatorPermutations(String[] operatorList, int i) {
        // Base case; if i is 0 then return an empty list
        if (i == 0) {
            List<String> base = new ArrayList<>();
            base.add("");
            return base;
        }

        // Recursive call to generate permutations of length i - 1
        List<String> permutations = findAllOperatorPermutations(operatorList, i - 1);
        List<String> results = new ArrayList<>();

        // For each permutation, add each operator to the end of the string
        for (String s : permutations) {
            for (String op : operatorList) {
                results.add(s + op);
            }
        }
        return results;
    }

    // Read in the input file
    private static void getInput(String name) {
        // Read in file
        File file = new File("./Data Files/" + name + ".txt");
        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr);) {

            String inputLine = br.readLine(); // Read in first line
            while (inputLine != null) { // Read in instructions until empty line
                long result = Long.parseLong(inputLine.split(":")[0]);
                List<Long> values = new ArrayList<Long>();
                String[] temp = inputLine.split(":")[1].trim().split(" ");
                for (String s : temp) {
                    values.add(Long.parseLong(s.trim()));
                }
                EQMAP.put(result, values);
                inputLine = br.readLine(); // Read in next line & repeat
            }
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

}
