# written by vosse064

# function to parse a FASTA file and return the concatenated sequence as a string
def parse_file(filename):
    sequence = ""
    with open(filename, "r") as file: 
        for line in file:
            line = line.strip()
            if not line.startswith(">"):  # Skip header lines
                continue
            sequence += line  # Append the sequence lines together
    return sequence

# String variables to hold the sequences from the FASTA files
healthy_sequence = parse_file("Human_HGG_Exons_Healthy.fasta")
mutated_sequence = parse_file("Human_HGG_Exons_Diseased.fasta")

print("Healthy Sequence Length:", len(healthy_sequence))
print("Mutated Sequence Length:", len(mutated_sequence))

stop_codon = ["TAA", "TAG", "TGA"] # List of stop codons to check against

mutation_found = False # Flag to indicate if a mutation has been found

for i in range(len(healthy_sequence)):

    if healthy_sequence[i] != mutated_sequence[i]: # Compare characters at the same position
        
        codon_start = (i // 3) * 3 # Calculate the start index of the codon

        healthy_codon = healthy_sequence[codon_start:codon_start + 3] # Extract the codon from the healthy sequence
        diseased_codon = mutated_sequence[codon_start:codon_start + 3] # Extract the codon from the mutated sequence

        if diseased_codon in stop_codon: # Check if the mutated codon is a stop codon
            print("Mutation found at Index " + str(codon_start) + ". Healthy Codon " + healthy_codon + " changed to Stop Codon " + diseased_codon)
            mutation_found = True # Set the flag to True if a mutation is found
            break # Exit the loop after finding the first mutation

if not mutation_found: # If no mutation was found, print a message
    print("No mutation found that results in a stop codon.")