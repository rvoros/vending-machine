package org.rvoros.vendingmachine;

public class Item {
	private Selector selector;
	private int amount;
	private double price;

	public Item(Selector selector, int amount, double price) {
		this.selector = selector;
		this.amount = amount;
		this.price = price;
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void decreaseAmount() {
		amount -= 1;
	}

	public boolean notEmpty() {
		return amount > 0;
	}
}
