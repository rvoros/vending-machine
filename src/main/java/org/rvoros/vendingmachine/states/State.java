package org.rvoros.vendingmachine.states;

import org.rvoros.vendingmachine.Coin;
import org.rvoros.vendingmachine.Selector;
import org.rvoros.vendingmachine.VendingMachine;

public interface State {
	void insertMoney(VendingMachine vendingMachine, Coin coin);
	void selectItem(VendingMachine vendingMachine, Selector selector);
	void turnOn(VendingMachine vendingMachine);
	void turnOff(VendingMachine vendingMachine);
	void coinReturn(VendingMachine vendingMachine);
}
