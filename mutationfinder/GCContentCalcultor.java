// written by vosse064
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;



public class GCContentCalcultor {

    public static void main(String[] args) throws FileNotFoundException {

        String intronsLength = "Human_TP53_Introns.fasta"; // File name for the introns FASTA file
        String exonsLength = "Human_TP53_Exons.fasta"; // File name for the exons FASTA file

        String intronsSequence = FASTAParsing.parseFASTA(intronsLength); // Parse the introns FASTA file and store the sequence
        String exonsSequence = FASTAParsing.parseFASTA(exonsLength); // Parse the exons FASTA file and store the sequence

        System.out.println("Introns Sequence length: " + intronsSequence.length()); // Print the length of the introns sequence
        System.out.println("Exons Sequence length: " + exonsSequence.length()); // Print the length of the exons sequence

        PrintWriter writer = new PrintWriter(new File("gc_data.csv")); // Create a PrintWriter to write the GC content data to a CSV file

        writer.println("Window_Index, intron_GC_percentage, exon_GC_percentage"); // Write the header for the CSV file

        int windowSize = 50; // Define the size of the sliding window for GC content calculation

        int length = Math.min(intronsSequence.length(), exonsSequence.length()); // Determine the length to iterate based on the shorter sequence

        for (int i = 0; i + windowSize <= length; i++) { // Iterate through the sequences using a sliding window approach

            String intronWindow = intronsSequence.substring(i, i + windowSize); // Extract the current window from the introns sequence
            String exonWindow = exonsSequence.substring(i, i + windowSize); // Extract the current window from the exons sequence

            double intronGCContent = calculateGCContent(intronWindow); // Calculate the GC content for the intron window
            double exonGCContent = calculateGCContent(exonWindow); // Calculate the GC content for the exon window

            writer.println(i + "," + intronGCContent + "," + exonGCContent); // Write the window index and GC content values to the CSV file

        }
        writer.close();

    }

    // Method to calculate the GC content of a given DNA sequence
    public static double calculateGCContent(String window) {

        int gcCount = 0; // Initialize count for 'G' and 'C' bases
 
        for(int i = 0; i < window.length(); i++) {
            char base = window.charAt(i); // Get the current character in the window
            if (base == 'G' || base == 'C') { // Check if the base is 'G' or 'C'
                gcCount++; // Increment the count for 'G' and 'C'
            }
        }

        return (double) gcCount / window.length() * 100; // Calculate and return the GC content as a percentage

    }
    
}
