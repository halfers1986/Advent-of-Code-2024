import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Part 1 answer:  426
 * Part 2 answer:  1359
 */
public class Day8 {

    // Initial input array
    private static Map<Coordinate, String> coordinateMap = new HashMap<Coordinate, String>();

    // To store the coordinates of the antinodes
    private static List<Coordinate> antinodes = new ArrayList<Coordinate>();
    private static List<Coordinate> antinodes2 = new ArrayList<Coordinate>();

    public static void main(String[] args) {
        getInput("Day8");
        findAntinodes();
        System.out.println("Number of Antinodes (Part 1): " + antinodes.size());
        System.out.println("Number of Antinodes (Part 2): " + antinodes2.size());

    }

    private static void findAntinodes() {
        for (Map.Entry<Coordinate, String> entry : coordinateMap.entrySet()) {
            Coordinate c1 = entry.getKey();
            if (!entry.getValue().equals(".")) {
                String value1 = entry.getValue();
                for (Map.Entry<Coordinate, String> entry2 : coordinateMap.entrySet()) {
                    Coordinate c2 = entry2.getKey();
                    if (entry2.getValue().equals(value1) && !c1.equals(c2)) {
                        List<Coordinate> line = calculateLine(c2, c1); // part 2
                        // Part 1
                        for (Coordinate c3 : line) {
                            if (!c3.equals(c1) && !c3.equals(c2)) {
                                double distance1 = calculateDistance(c1, c3);
                                double distance2 = calculateDistance(c2, c3);
                                if (distance1 == 2 * distance2 || distance2 == 2 * distance1) {
                                    if (!antinodes.contains(c3)) {
                                        antinodes.add(c3);
                                    }
                                }
                            }
                        }
                        // Part 2
                        for (Coordinate c3 : line) {
                            if (!antinodes2.contains(c3)) {
                                antinodes2.add(c3);
                            }
                        }
                    }
                }
            }
        }
    }

    private static List<Coordinate> calculateLine(Coordinate c2, Coordinate c1) {
        List<Coordinate> line = new ArrayList<Coordinate>();
        int dx = c2.getX() - c1.getX();
        int dy = c2.getY() - c1.getY();

        Coordinate c = new Coordinate(c1.getX() - dx, c1.getY() - dy);

        while (coordinateMap.containsKey(c)) {
            if (!line.contains(c)) {
                line.add(c);
            }
            c = new Coordinate(c.getX() - dx, c.getY() - dy);
        }

        c = new Coordinate(c1.getX() + dx, c1.getY() + dy);
        while (coordinateMap.containsKey(c)) {
            if (!line.contains(c)) {
                line.add(c);
            }
            c = new Coordinate(c.getX() + dx, c.getY() + dy);
        }
        return line;
    }

    // Calculate distance (d) between any two points c1(x1,y1) and c2(x2,y2)
    private static double calculateDistance(Coordinate c1, Coordinate c2) {
        // d = sqrt((x2 - x1)^2 + (y2 - y1)^2)
        // a = (x2 - x1)^2
        // b = (y2 - y1)^2
        // d = sqrt(a + b)
        double a = Math.pow(c2.getX() - c1.getX(), 2);
        double b = Math.pow(c2.getY() - c1.getY(), 2);
        return Math.sqrt(a + b);
    }


    // Read the input file and store it in a 2d ArrayList
    private static void getInput(String name) {
        File file = new File("./Data Files/" + name + ".txt");
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);

            String inputLine = br.readLine(); // Read in first line
            int i = 0;
            while (inputLine != null) { // Read in instructions until empty line
                String[] mapLine = inputLine.split("");
                for (int j = 0; j < mapLine.length; j++) {
                    Coordinate c = new Coordinate(j, i);
                    coordinateMap.put(c, mapLine[j]);
                }
                i++;
                inputLine = br.readLine(); // Read in next line & repeat
            }
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
}
