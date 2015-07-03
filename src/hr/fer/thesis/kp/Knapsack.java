package hr.fer.thesis.kp;

import java.util.ArrayList;
import java.util.List;

public class Knapsack {

	private double capacity;
	private List<Item> items;
	private double totalValue;
	private double totalWeight;

	public Knapsack(double capacity) {
		this.capacity = capacity;
		items = new ArrayList<>();
	}

	public double getCapacity() {
		return capacity;
	}

	public List<Item> getItems() {
		return items;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public double getTotalWeight() {
		return totalWeight;
	}
	
	public double getFreeSpace() {
		return capacity - totalWeight;
	}
	
	public boolean isOverWeighted(){
		if(getFreeSpace() < 0) {
			return true;
		}
		return false;
	}

	public void addItem(Item item) {
			items.add(item);
			totalValue += item.getValue();
			totalWeight += item.getWeight();
	}
	
}
