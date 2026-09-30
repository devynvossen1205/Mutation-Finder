# written by vosse064

import matplotlib.pyplot as plt
import csv

# Read the gc_data.csv file
window_indices = []
intron_gc = []
exon_gc = []

# Open the CSV file and read the data
with open("gc_data.csv", "r") as file: 
    reader = csv.DictReader(file)  # DictReader uses the header row as keys
    for row in reader: # Iterate through each row in the CSV file and append the values to the respective lists
        window_indices.append(int(row["Window_Index"]))
        intron_gc.append(float(row[" intron_GC_percentage"]))
        exon_gc.append(float(row[" exon_GC_percentage"]))

# Generate the line plot
plt.figure(figsize=(12, 6))

# Plot intron and exon lines with different colors
plt.plot(window_indices, intron_gc, color="blue", label="Intron")
plt.plot(window_indices, exon_gc, color="red", label="Exon")

# Add labels, title, and legend
plt.xlabel("Window Index (Position in Genome)")
plt.ylabel("GC Content Percentage (%)")
plt.title("GC Content Analysis: TP53 Intron vs Exon")
plt.legend()
plt.grid(True, linestyle="--", alpha=0.5)

plt.savefig("gc_plot.png")

plt.show()
