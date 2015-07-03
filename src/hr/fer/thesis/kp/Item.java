package hr.fer.thesis.kp;

public class Item implements Comparable<Item> {

	private double value;
	private double weight;
	
	private int index;

	public Item(double value, double wight, int index) {
		this.value = value;
		this.weight = wight;
		this.index = index;
	}

	public double getValue() {
		return value;
	}

	public double getWeight() {
		return weight;
	}
	
	public int getIndex() {
		return index;
	}

	@Override
	public int compareTo(Item other) {
		if (this.getValue() < other.getValue()) {
			return -1;
		} else if (this.getValue() > other.getValue()) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "Item with value " + value + " and weight " + weight;
	}



}
