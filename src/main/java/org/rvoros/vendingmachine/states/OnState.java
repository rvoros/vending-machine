package org.rvoros.vendingmachine.states;

import org.rvoros.vendingmachine.Coin;
import org.rvoros.vendingmachine.Selector;
import org.rvoros.vendingmachine.VendingMachine;

public class OnState implements State {

	public void insertMoney(VendingMachine vendingMachine, Coin coin) {
		// change to vending state
		vendingMachine.setState(VendingMachine.VENDING);
		// insert the money
		vendingMachine.insertMoney(coin);
	}

	public void selectItem(VendingMachine vendingMachine, Selector selector) {
		// There is no coin inserted yet, so nothing to do here
	}

	public void turnOn(VendingMachine vendingMachine) {
		// We are in state ON already		
	}

	public void turnOff(VendingMachine vendingMachine) {
		vendingMachine.setState(VendingMachine.OFF);
	}

	public void coinReturn(VendingMachine vendingMachine) {
		// Nothing to return in this state	
	}
}
