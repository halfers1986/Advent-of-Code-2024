
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Part 1 answer:  2297
 * Part 2 answer:  1745
 */
public class Day4 {

    private static List<String[]> inputArray = new ArrayList<String[]>();
    private static Map<Coordinate, String> coordinateMap = new HashMap<Coordinate, String>();

    public static void main(String[] args) {
        getInput("Day4");
        mapStringsToCoordinates(inputArray);
        int sum = findXmas(coordinateMap);
        System.out.println("Answer to part 1 = " + sum);
        int sum2 = findXmas2(coordinateMap);
        System.out.println("Answer to part 2 = " + sum2);
    }

    private static int findXmas2(Map<Coordinate, String> coordinateMap) {
        int sum = 0;

        for (Coordinate c : coordinateMap.keySet()) {
            if (coordinateMap.get(c).equals("A")) {
                Coordinate[] adjacent = c.getAdjacent();
                Coordinate northEast = adjacent[2];
                Coordinate southEast = adjacent[7];
                Coordinate southWest = adjacent[5];
                Coordinate northWest = adjacent[0];
                if (coordinateMap.containsKey(northEast) && coordinateMap.containsKey(southEast) && coordinateMap.containsKey(southWest) && coordinateMap.containsKey(northWest)) {
                    // Check if opposite corners are M and S
                    if (((coordinateMap.get(northEast).equals("M") && coordinateMap.get(southWest).equals("S")) ||
                    (coordinateMap.get(southWest).equals("M") && coordinateMap.get(northEast).equals("S"))) &&
                    ((coordinateMap.get(southEast).equals("M") && coordinateMap.get(northWest).equals("S")) ||
                    (coordinateMap.get(northWest).equals("M") && coordinateMap.get(southEast).equals("S")))) {
                        sum++;
                    }
                }
            }
        }

        return sum;
    }

    // Used in part 1
    // Find the number of times "XMAS" appears in the map in a straight line
    private static int findXmas(Map<Coordinate, String> coordinateMap) {
        int sum = 0;
        for (Coordinate c : coordinateMap.keySet()) {
            if (coordinateMap.get(c).equals("X")) {
                Coordinate[] adjacent = c.getAdjacent();
                for (int i = 0; i < adjacent.length; i++) {
                    if (coordinateMap.containsKey(adjacent[i]) && coordinateMap.get(adjacent[i]).equals("M")) {
                        Coordinate M = adjacent[i];
                        Coordinate[] adjacentM = M.getAdjacent();
                        Coordinate straightLine = adjacentM[i];
                        if (coordinateMap.containsKey(straightLine) && coordinateMap.get(straightLine).equals("A")) {
                            Coordinate A = straightLine;
                            Coordinate[] adjacentA = A.getAdjacent();
                            Coordinate finalDestination = adjacentA[i];
                            if (coordinateMap.containsKey(finalDestination)
                                    && coordinateMap.get(finalDestination).equals("S")) {
                                sum++;
                            }
                        }
                    }
                }
            }
        }
        return sum;
    }

    // Map the 2D array of Strings to Coordinates representing their position
    private static void mapStringsToCoordinates(List<String[]> inputArray) {
        for (int i = 0; i < inputArray.size(); i++) {
            String[] lettersArray = inputArray.get(i);
            for (int j = 0; j < lettersArray.length; j++) {
                Coordinate c = new Coordinate(j, i);
                coordinateMap.put(c, lettersArray[j]);
            }
        }
    }

    // Read in file and store values (lines, split to 1 char Strings) in a 2D array
    private static void getInput(String name) {
        // Read in file
        File file = new File("./Data Files/" + name + ".txt");
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);

            String inputLine = br.readLine(); // Read in first line
            while (inputLine != null) {
                String[] lettersArray = inputLine.split("");
                inputArray.add(lettersArray);
                inputLine = br.readLine(); // Read in next line & repeat
            }
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
}