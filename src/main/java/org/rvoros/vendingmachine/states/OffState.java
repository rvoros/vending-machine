package org.rvoros.vendingmachine.states;

import org.rvoros.vendingmachine.Coin;
import org.rvoros.vendingmachine.Selector;
import org.rvoros.vendingmachine.VendingMachine;

public class OffState implements State {

	public void insertMoney(VendingMachine vendingMachine, Coin coin) {
		// simply return the coin
		vendingMachine.getResponseListener().returnCoin(coin);
		vendingMachine.getInsertedMoney().clear();
	}

	public void selectItem(VendingMachine vendingMachine, Selector selector) {
		// machine is turned off so just ignore it
	}

	public void turnOn(VendingMachine vendingMachine) {
		vendingMachine.setState(VendingMachine.ON);
	}

	public void turnOff(VendingMachine vendingMachine) {
		// nothing to do we are already in state OFF
	}

	public void coinReturn(VendingMachine vendingMachine) {
		// No inserted coin in this case
	}
}
