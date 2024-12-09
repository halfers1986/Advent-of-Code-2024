import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
 * Part 1 answer:  6395800119709
 * Part 2 answer:  6418529470362
 */
public class Day9 {

    private static List<String> diskRepresentation;

    public static void main(String[] args) {

        getInput("Day9");
        List<String> diskBlocks = readRepresentation(diskRepresentation);
        sortDisk(diskBlocks);
        long total = checkSum(diskBlocks);
        System.out.println("Answer to part 1: " + total);
        diskBlocks = readRepresentation(diskRepresentation); // Reset the disk for part 2
        wholeFileSortDisk(diskBlocks);
        total = checkSum(diskBlocks);
        System.out.println("Answer to part 2: " + total);
    }

    private static void wholeFileSortDisk(List<String> diskBlocks) {
     
        for (int j = diskBlocks.size() - 1; j >= 0; j--) { // Start from the end of the disk
            // System.out.println(diskBlocks.toString());
            if (!diskBlocks.get(j).equals(".")) { // if j is a used block
                // Get the block ID and the length of the block
                String blockID = diskBlocks.get(j);
                int IDBlockEnd = j;
                while (j-1 >=0 && diskBlocks.get(j-1).equals(blockID)) {
                    j--;
                }
                int IDBlockStart = j;
                int IDBlockSize = (IDBlockEnd - IDBlockStart)+1;
                int i = 0; // Reset i to start from the beginning of the disk each time
                for (i = 0; i < diskBlocks.size(); i++) { // Start from the beginning of the disk
                    if (diskBlocks.get(i).equals(".")) { // if i is a free block
                        int spaceBlockStart = i;
                        while (i+1 < diskBlocks.size() && diskBlocks.get(i+1).equals(".")) {
                            i++;
                        }
                        int spaceBlockEnd = i;
                        if (spaceBlockEnd >= IDBlockStart) {
                            break;
                        }
                        int spaceBlockLength = (spaceBlockEnd - spaceBlockStart) + 1;
                        if (spaceBlockLength >= IDBlockSize) {
                            for (int k = spaceBlockStart; k <= spaceBlockStart + IDBlockSize-1; k++) {
                                diskBlocks.set(k, blockID);
                            }
                            for (int k = IDBlockStart; k <= IDBlockEnd; k++) {
                                diskBlocks.set(k, ".");
                            }
                            break; // If we have moved a block, no need to look for more spaces.
                        }
                    }
                }
            }

        }
    }

    private static long checkSum(List<String> diskBlocks) {
        long total = 0;
        for (int i = 0; i < diskBlocks.size(); i++) {
            if (diskBlocks.get(i).equals(".")) {
                continue;
            }
            total += Integer.parseInt(diskBlocks.get(i)) * i;
        }
        return total;
    }

    private static void sortDisk(List<String> diskBlocks) {
        int i = 0;
        int j = diskBlocks.size() - 1;
        for (i = 0; i < diskBlocks.size(); i++) { // Start from the beginning of the disk
            if (diskBlocks.get(i).equals(".")) { // if i is a free block
                for (j = diskBlocks.size() - 1; j > i; j--) { // Start from the end of the disk
                    if (!diskBlocks.get(j).equals(".")) { // if j is a used block
                        // swap i and j
                        String temp = diskBlocks.get(i);
                        diskBlocks.set(i, diskBlocks.get(j));
                        diskBlocks.set(j, temp);
                        break; // If we find a used block, we can stop looking for more
                    }
                }
            }
            if (i == j) {
                break; // If we reach the same index, we can stop
            }
        }
    }

    private static List<String> readRepresentation(List<String> diskRepresentation) {
        List<String> diskBlocks = new ArrayList<>();

        int fileNumber = 0;
        for (int i = 0; i < diskRepresentation.size(); i++) {
            int multiplier = Integer.parseInt(diskRepresentation.get(i));
            if (i % 2 == 0) {
                for (int j = 0; j < multiplier; j++) {
                    diskBlocks.add(String.valueOf(fileNumber));
                }
                fileNumber++;
            } else {
                for (int j = 0; j < multiplier; j++) {
                    diskBlocks.add(".");
                }
            }
        }
        return diskBlocks;
    }

    private static void getInput(String name) {
        File file = new File("./Data Files/" + name + ".txt");
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);

            // This file only has one line, so no need to loop
            String inputLine = br.readLine();
            diskRepresentation = Arrays.asList(inputLine.split(""));

        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

}