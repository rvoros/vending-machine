package org.rvoros.vendingmachine;

public enum Coin{
	TEN_PENCE(0.1),
	TWENTY_PENCE(0.2),
	FIFTY_PENCE(0.5),
	ONE_POUND(1);

	final private double value;

	private Coin(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}
}
