package org.rvoros.vendingmachine.responses;

import org.rvoros.vendingmachine.Coin;
import org.rvoros.vendingmachine.Selector;

/**
 * Listener for the valid responses from the vending machine.
 */
public interface ResponseListener {
	/**
	 * Returning coins.
	 * 
	 * @param coin the coin returned
	 */
	void returnCoin(Coin coin);

	/**
	 * Vending an item.
	 * 
	 * @param selector item selector
	 */
	void vendItem(Selector selector);
}
