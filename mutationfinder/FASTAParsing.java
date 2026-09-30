// written by vosse064
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class FASTAParsing {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in); // Scanner for user input
        
        System.out.println("Enter the file location of the first FASTA file:"); // Prompt user for the first FASTA file location
        String fileLocation1 = scanner.nextLine();
        String humanSequence = parseFASTA(fileLocation1); // Parse the first FASTA file and store the sequence

        System.out.println("Enter the file location of the second FASTA file:"); // Prompt user for the second FASTA file location
        String fileLocation2 = scanner.nextLine();
        String zebrafish = parseFASTA(fileLocation2); // Parse the second FASTA file and store the sequence

        System.out.println("Human Sequence: " + humanSequence);
        System.out.println("Zebrafish Sequence: " + zebrafish);

        System.out.println("Enter an integer k for k-mer analysis:"); // Prompt user for the integer k for k-mer analysis
        int k = scanner.nextInt();

        System.out.println("Top 5 most common k-mers in the human sequence: ");
        printKmers(humanSequence, k); // Print the top 5 most common k-mers in the human sequence

        System.out.println("Top 5 most common k-mers in the zebrafish sequence: ");
        printKmers(zebrafish, k); // Print the top 5 most common k-mers in the zebrafish sequence

        scanner.close();
    }
    
    // Method to parse a FASTA file and return the sequence as a string
    public static String parseFASTA(String fileLocation) throws FileNotFoundException {
        StringBuilder sequence = new StringBuilder(); // StringBuilder to efficiently build the sequence string
        Scanner fileScanner = new Scanner(new File(fileLocation)); // Scanner to read the FASTA file
        
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().trim();
            // Skip header lines that start with '>'
            if (!line.isEmpty() && !line.startsWith(">")) { // Check if the line is not empty and does not start with '>'
                sequence.append(line.replaceAll("\\s+", "")); // Remove any whitespace and append the line to the sequence
            }
        }
        
        fileScanner.close();
        return sequence.toString();
    }

    // Method to print all k-mers of the given sequence
    public static void printKmers(String sequence, int k) {
        HashMap<String, Integer> kmerCounts = new HashMap<>(); // HashMap to store k-mer counts

        // Iterate through the sequence to extract k-mers and count their occurrences
        for (int i = 0; i <= sequence.length() - k; i++) {
            String kmer = sequence.substring(i, i + k); // Extract the k-mer from the sequence
            if (kmerCounts.containsKey(kmer)) {
                kmerCounts.put(kmer, kmerCounts.get(kmer) + 1); // Increment count if k-mer already exists
            } else {
                kmerCounts.put(kmer, 1); // Initialize count for new k-mer
            }
        }

        // Convert the k-mer counts to a list for sorting
        ArrayList<String> sortedKmers = new ArrayList<>(kmerCounts.keySet()); // Create a list of k-mers for sorting

        // Sort the k-mers based on their counts using bubble sort
        for (int i = 0; i < sortedKmers.size() - 1; i++) { 
            for (int j = 0; j < sortedKmers.size() - 1 - i; j++) {
                if (kmerCounts.get(sortedKmers.get(j)) > kmerCounts.get(sortedKmers.get(j + 1))) {
                    String temp = sortedKmers.get(j);
                    sortedKmers.set(j, sortedKmers.get(j + 1));
                    sortedKmers.set(j + 1, temp);
                }
            }
        }

        // Print the top 5 most common k-mers
        for (int i = sortedKmers.size() - 1; i >= sortedKmers.size() - 5 && i >= 0; i--) {
            String kmer = sortedKmers.get(i);
            System.out.println(kmer + ": " + kmerCounts.get(kmer)); // Print the k-mer and its count
        }

        // Print the bottom 5 least common k-mers
        for (int i = 0; i < 5 && i < sortedKmers.size(); i++) {
            String kmer = sortedKmers.get(i);
            System.out.println(kmer + ": " + kmerCounts.get(kmer)); // Print the k-mer and its count
        }

    }
}
