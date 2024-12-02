
/*
 * Part 1 answer:  356
 * Part 2 answer:  413
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 {

    private static List<int[]> reports = new ArrayList<int[]>();

    public static void main(String[] args) {
        getInput("Day2");
        int safeReports = countSafeReports();
        System.out.println("Number of safe reports: " + safeReports);
    }

    // Count number of safe reports (used in parts 1 & 2)
    private static int countSafeReports() {
        int count = 0;
        for (int[] report : reports) {
            boolean isSafe = calculateSafety(report);
            boolean isTolerable = false; // Used for part 2
            if (!isSafe) {
                for (int i = 0; i < report.length; i++) {
                    int[] newReport = new int[report.length - 1];
                    int index = 0;
                    for (int j = 0; j < report.length; j++) {
                        if (j != i) {
                            newReport[index] = report[j];
                            index++;
                        }
                    }
                    isTolerable = calculateSafety(newReport);
                    if (isTolerable) {
                        break;
                    }
                }
            }
            if (isSafe || isTolerable) {
                count++;
            }
        }
        return count;
    }


    // Check if report is safe (used in part 1 & 2)
    private static boolean calculateSafety(int[] report) {
        int descending = 0;
        int ascending = 0;
        for (int i = 0; i < report.length - 1; i++) {
            if (Math.abs(report[i] - report[i + 1]) > 3 || report[i] == report[i + 1]) {
                // If difference between two numbers is greater than 3 or they are the same,
                // then not safe
                return false;
            }
            if (report[i] > report[i + 1]) {
                descending++;
            } else if (report[i] < report[i + 1]) {
                ascending++;
            }
        }
        if (ascending > 0 && descending > 0) {
            // If there are both ascending and descending values, then not safe
            return false;
        } else {
            // Otherwise, safe
            return true;
        }
    }

    // Read in file and store values (lines) in a list of arrays
    private static void getInput(String name) {
        // Read in file
        File file = new File("./Data Files/" + name + ".txt");
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);

            String inputLine = br.readLine(); // Read in first line
            while (inputLine != null) {
                String fixedString = inputLine.replaceAll(" +", " "); // Replaces multiple spaces with a single space
                String[] splitLine = fixedString.split(" ");
                int[] intArray = new int[splitLine.length];
                for (int i = 0; i < splitLine.length; i++) { // make array of integers so that don't have to parse
                                                             // string in other methods
                    intArray[i] = Integer.parseInt(splitLine[i]);
                }
                reports.add(intArray);
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

}
