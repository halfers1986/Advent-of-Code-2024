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
 * Part 1 answer:  5239
 * Part 2 answer:  1753
 */
public class Day6 {

    // Initial input arrays
    private static List<String[]> map2dArrayStyle = new ArrayList<String[]>();
    private static Map<Coordinate, String> coordinateMap = new HashMap<Coordinate, String>();

    public static void main(String[] args) {
        getInput("Day6");
        mapStringsToCoordinates(map2dArrayStyle);

        // Part 1
        try {
            plotGuardMovements();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        int countOfPositions = 0;
        for (Map.Entry<Coordinate, String> entry : coordinateMap.entrySet()) {
            if (entry.getValue().equals("X")) {
                countOfPositions++;
            }
        }
        System.out.println("Number of positions visited (Answer to Part 1): " + countOfPositions);

        // Part 2
        // reset the map
        coordinateMap.clear();
        mapStringsToCoordinates(map2dArrayStyle);
        int numberOfPossibleLoops = 0;
        try {
            numberOfPossibleLoops = findPossibleLoops();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Number of possible loops (Answer to Part 2): " + numberOfPossibleLoops);
    }

    private static int findPossibleLoops() throws IllegalStateException {
        ArrayList<Coordinate> loopCreatingBlocks = new ArrayList<Coordinate>();
        ArrayList<Coordinate> checkedPositions = new ArrayList<Coordinate>();

        String[] directions = { "^", ">", "v", "<" };
        Coordinate guardPosition = null;
        String guardVector = null;

        // Loop through the map of coordinates to find the guard's starting position
        for (Map.Entry<Coordinate, String> entry : coordinateMap.entrySet()) {
            if (Arrays.asList(directions).contains(entry.getValue())) {
                guardPosition = entry.getKey();
                guardVector = entry.getValue();
                break;
            }
        }

        // Create a guard object
        if (guardPosition == null || guardVector == null) {
            throw new IllegalStateException("Guard's starting position or direction is not found.");
        }
        Guard guard = new Guard(guardVector, guardPosition);

        // Move the guard
        while (coordinateMap.containsKey(guard.getGuardPosition())) {
            // Coordinate initialPosition = guard.getGuardPosition();
            Coordinate updatedPosition = guard.moveGuard();

            // If the next move already hits an obstruction, rotate the direction
            while (coordinateMap.containsKey(updatedPosition) && coordinateMap.get(updatedPosition).equals("#")) {
                guard.rotateVector();
                updatedPosition = guard.moveGuard();
            }

            // If the new position is in the map and isn't an obstruction, check if it could
            // be a loop if an obstruction is added
            if (coordinateMap.containsKey(updatedPosition) && !checkedPositions.contains(updatedPosition)) {
                checkedPositions.add(updatedPosition);
                if (checkForLoop(guard)) {
                    if (!loopCreatingBlocks.contains(updatedPosition) && !updatedPosition.equals(guardPosition)) {
                        loopCreatingBlocks.add(updatedPosition);
                    }
                }
                // Reset the map and re-map the coordinates, but keep the guard's position and direction
                coordinateMap.clear();
                mapStringsToCoordinates(map2dArrayStyle);
            }

            // Now the possible loop has been checked, move the guard to the next position
            guard.setGuardPosition(updatedPosition);
        }

        return loopCreatingBlocks.size();
    }

    private static Boolean checkForLoop(Guard guard) {

        // Save the guard's initial position and direction before moving
        Coordinate preLoopPosition = guard.getGuardPosition();
        String preLoopDirection = guard.getGuardDirection();

        // Create a block at the first move position
        Coordinate blockPosition = guard.moveGuard();
        coordinateMap.put(blockPosition, "#");
        List<String> visited = new ArrayList<String>();

        // Move the guard
        while (true) {

            Coordinate initialPosition = guard.getGuardPosition();
            String initialDirection = guard.getGuardDirection();
            Coordinate updatedPosition = guard.moveGuard();

            // If the guard has returned to a previous position and direction
            String positionDirection = initialPosition.toString() + initialDirection; // Combined position and direction
            if (visited.contains(positionDirection)) {
                // reset the guard's position and direction
                guard.setGuardPosition(preLoopPosition);
                guard.setGuardDirection(preLoopDirection);
                return true;
            } else {
                visited.add(positionDirection); // Store the guard's position and direction
            }

            // If the move hits an obstruction
            while (coordinateMap.containsKey(updatedPosition) && coordinateMap.get(updatedPosition).equals("#")) {
                // Otherwise, rotate the direction and move the guard
                guard.rotateVector();
                updatedPosition = guard.moveGuard(); // After rotating, move the guard
            }

            // Once no obstruction found, set the new position
            guard.setGuardPosition(updatedPosition);

            // If the guard has left the map
            if (!coordinateMap.containsKey(updatedPosition)) {
                // reset the guard's position and direction
                guard.setGuardPosition(preLoopPosition);
                guard.setGuardDirection(preLoopDirection);
                return false;
            }
            // While-loop repeats until guard has returned to a previous position and direction
            // or left the map
        }
    }

    private static void plotGuardMovements() throws IllegalStateException {

        String[] directions = { "^", ">", "v", "<" };
        Coordinate guardPosition = null;
        String guardVector = null;

        // Loop through the map of coordinates to find the guard's starting position
        for (Map.Entry<Coordinate, String> entry : coordinateMap.entrySet()) {
            if (Arrays.asList(directions).contains(entry.getValue())) {
                guardPosition = entry.getKey();
                guardVector = entry.getValue();
                // System.out.println("Guard's starting position: " + guardPosition.getX() + ",
                // " + guardPosition.getY());
                break;
            }
        }

        // Create a guard object
        if (guardPosition == null || guardVector == null) {
            throw new IllegalStateException("Guard's starting position or direction is not found.");
        }
        Guard guard = new Guard(guardVector, guardPosition);

        // Move the guard
        while (coordinateMap.containsKey(guard.getGuardPosition())) {
            Coordinate initialPosition = guard.getGuardPosition();
            Coordinate updatedPosition = guard.moveGuard();
            while (coordinateMap.containsKey(updatedPosition) && coordinateMap.get(updatedPosition).equals("#")) {
                guard.rotateVector();
                updatedPosition = guard.moveGuard();
            }

            guard.setGuardPosition(updatedPosition);
            coordinateMap.put(initialPosition, "X");
            if (coordinateMap.containsKey(updatedPosition)) {
                coordinateMap.put(updatedPosition, guard.getGuardDirection());
            }
        }
    }

    // Map the 2D array of Strings to Coordinates representing their position
    private static void mapStringsToCoordinates(List<String[]> inputArray) {
        for (int i = 0; i < inputArray.size(); i++) {
            String[] iconArray = inputArray.get(i);
            for (int j = 0; j < iconArray.length; j++) {
                Coordinate c = new Coordinate(j, i);
                coordinateMap.put(c, iconArray[j]);
            }
        }
    }

    // Read in the input file
    private static void getInput(String name) {
        // Read in file
        File file = new File("./Data Files/" + name + ".txt");
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);

            String inputLine = br.readLine(); // Read in first line
            while (inputLine != null) { // Read in instructions until empty line
                String[] mapLine = inputLine.split("");
                map2dArrayStyle.add(mapLine);
                inputLine = br.readLine(); // Read in next line & repeat
            }
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
}
