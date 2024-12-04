import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Part 1 answer:  167090022
 * Part 2 answer:  89823704
 */
public class Day3 {

    private static String inputString = "";

    public static void main(String[] args) {
        getInput("Day3");
        // Part 1
        String cleanString = cleanInput(inputString);
        String numberString = makeNumberString(cleanString);
        String[] toMultiplyStrings = numberString.trim().split(" ");
        List<int[]> toMultiply = parseNumbers(toMultiplyStrings);
        int total = multiplyAndSumNumbers(toMultiply);
        System.out.println("Answer 1: " + total);

        // Part 2
        String cleanString2 = cleanInput2(inputString);
        String[] instructionsArray = cleanString2.split(" ");
        List<String> instructionsFollowed = followInstructions(instructionsArray);
        int total2 = multiplyAndSumNumbers2(instructionsFollowed);
        System.out.println("Answer 2: " + total2);
    }

    // Used in part 2
    private static int multiplyAndSumNumbers2(List<String> instructionsFollowed) {
        int sum = 0;
        for (String s : instructionsFollowed) {
            StringBuilder numberString = new StringBuilder();
            Pattern pattern = Pattern.compile("\\d{1,3}");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                numberString.append(" " + matcher.group());
            }
            String[] numbers = numberString.toString().trim().split(" ");
            int product = Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
            sum += product;
        }
        return sum;
    }

    // Used in part 2
    private static List<String> followInstructions(String[] instructionsArray) {
        List<String> instructionsFollowed = new ArrayList<String>();
        Boolean doFlag = true;
        for (int i = 0; i < instructionsArray.length; i++) {
            String instruction = instructionsArray[i];
            if (instruction.equals("do()")) {
                doFlag = true;
            } else if (instruction.equals("don't()")) {
                doFlag = false;
            } else {
                if (instruction.contains("mul") && doFlag) {
                    instructionsFollowed.add(instruction);
                }
            }
        }
        return instructionsFollowed;
    }

    // Used in part 1
    private static int multiplyAndSumNumbers(List<int[]> toMultiply) {
        int sum = 0;
        for (int[] numbers : toMultiply) {
            int product = numbers[0] * numbers[1];
            sum += product;
        }
        return sum;
    }

    // Used in part 1
    private static List<int[]> parseNumbers(String[] toMultiplyStrings) {
        List<int[]> toMultiply = new ArrayList<int[]>();
        for (String s : toMultiplyStrings) {
            s.trim();
            // System.out.println("String to split: " + s);
            String[] stringNumbers = s.split(",");
            int[] numbers = new int[] { Integer.parseInt(stringNumbers[0]), Integer.parseInt(stringNumbers[1]) };
            // System.out.println("Numbers: " + numbers[0] + " " + numbers[1]);
            toMultiply.add(numbers);
        }
        return toMultiply;
    }

    // Used in part 1
    private static String makeNumberString(String cleanString) {
        StringBuilder numberString = new StringBuilder();
        Pattern pattern = Pattern.compile("\\d{1,3},\\d{1,3}");
        Matcher matcher = pattern.matcher(cleanString);
        while (matcher.find()) {
            numberString.append(" " + matcher.group());
        }
        return numberString.toString();
    }

    // used in part 2
    private static String cleanInput2(String inputString) {
        StringBuilder cleanString = new StringBuilder();
        Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)");
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            cleanString.append(" " + matcher.group());
        }
        return cleanString.toString().trim();
    }

    // used in part 1
    private static String cleanInput(String inputString) {
        StringBuilder cleanString = new StringBuilder();
        Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            cleanString.append(matcher.group());
        }
        return cleanString.toString();
    }

    // Read in file and store values (lines) in a String
    private static void getInput(String name) {
        // Read in file
        File file = new File("./Data Files/" + name + ".txt");
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);

            String inputLine = br.readLine(); // Read in first line
            while (inputLine != null) {
                inputString = inputString + inputLine;
                inputLine = br.readLine(); // Read in next line & repeat
            }
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
}
