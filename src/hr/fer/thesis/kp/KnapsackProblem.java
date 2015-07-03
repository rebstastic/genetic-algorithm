package hr.fer.thesis.kp;

import hr.fer.thesis.ga.Chromosome;
import hr.fer.thesis.ga.GAData;
import hr.fer.thesis.ga.GeneticAlgorithm;
import hr.fer.thesis.gui.ParameterChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class KnapsackProblem {

	private static JFrame frame;
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				frame = new ParameterChooser();
				frame.setVisible(true);
			}
		});

	}

	public static void solve(GeneticAlgorithm ga, String destination) {
		KPData.storeDataFromFile(GAData.file);

		Random random = new Random();
		GAData.random = random;

		long start = System.nanoTime();
		Chromosome best = ga.run();
		long end = System.nanoTime();
		double total = (end - start) / 1000000000.;

		writeToFile(ga, destination, total, best);

	}

	private static void writeToFile(GeneticAlgorithm ga, String dest,
			double time, Chromosome solution) {
		File file = new File(dest);

		if (!file.isDirectory()) {
			dest = dest.substring(0, dest.length() - 4);
			String newName = new String(dest);
			int i = 1;
			while (file.exists()) {
				newName = dest + "(" + i + ")" + ".txt";
				file = new File(newName);
				i++;
			}
		} else {
			dest += "/out.txt";
			file = new File(dest);
			dest = dest.substring(0, dest.length() - 4);
			String newName = new String(dest);
			int i = 1;
			while (file.exists()) {
				newName = dest + "(" + i + ")" + ".txt";
				file = new File(newName);
				i++;
			}
		}

		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), StandardCharsets.UTF_8))) {

			bw.write("KP intance:");
			bw.newLine();

			bw.write("Capacity: " + KPData.capacity);
			bw.newLine();
			for (Item item : KPData.items) {
				bw.write(item.toString());
				bw.newLine();
			}

			bw.newLine();
			bw.write("GA parameters:");
			bw.newLine();

			bw.write("Population size: " + GAData.populationSize);
			bw.newLine();
			bw.write("pc: " + GAData.pc);
			bw.newLine();
			bw.write("pm: " + GAData.pm);
			bw.newLine();
			bw.write("Number of iterations: " + GAData.iterations);
			bw.newLine();
			bw.newLine();
			bw.write("Initial population strategy: "
					+ ga.getInitialPopulation().getName());
			bw.newLine();
			bw.write("Selection strategy: " + ga.getSelection().getName());
			bw.newLine();
			bw.write("Crossover strategy: " + ga.getCrossover().getName());
			bw.newLine();
			bw.write("Replacement strategy: " + ga.getReplacement().getName());
			bw.newLine();
			bw.write("Elitism: " + (GAData.elitism ? "Yes" : "No"));
			bw.newLine();
			bw.write("Stopping criteria strategy: "
					+ ga.getStoppingCriteria().getName());
			bw.newLine();
			bw.newLine();

			bw.write("Time: "
					+ new DecimalFormat("###.##########").format(time) + "s");
			bw.newLine();

			Knapsack knapsack = new Knapsack(KPData.capacity);
			for (int i = 0; i < solution.size(); i++) {
				if (solution.getGene(i)) {
					knapsack.addItem(KPData.items.get(i));
				}
			}

			bw.write("Total value: " + knapsack.getTotalValue());
			bw.newLine();
			bw.write("Total weight: " + knapsack.getTotalWeight());
			bw.newLine();

			bw.write("Solution: " + solution);
			
			bw.flush();

		} catch (Exception ex) {
			JOptionPane.showConfirmDialog(frame, "Error occured.", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
}
