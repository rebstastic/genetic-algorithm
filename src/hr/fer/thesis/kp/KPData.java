package hr.fer.thesis.kp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KPData {

	public static double capacity;
	public static List<Item> items;

	public static int size() {
		return items.size();
	}

	public static void storeDataFromFile(String file) {

		items = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			int index = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#"))
					continue;
				String[] lineData = line.split("\\s+");
				if (lineData.length == 1) {
					capacity = Double.parseDouble(lineData[0]);
				} else {
					try {
						double value = Double.parseDouble(lineData[0]);
						double weight = Double.parseDouble(lineData[1]);
						items.add(new Item(value, weight, index++));
					} catch (NumberFormatException e) {
						System.err
								.println("Invalid input file. All values and weights must be doubles.");
						System.exit(1);
					}
				}
			}
		} catch (IOException e) {
			System.err.println("IOException has occured: " + e.getMessage());
			System.exit(1);
		}
	}

}
