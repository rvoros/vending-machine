package org.rvoros.vendingmachine.responses;

import org.rvoros.vendingmachine.Coin;
import org.rvoros.vendingmachine.Selector;

/**
 * Defualt implementation of {@link ResponseListener}. It does basically nothing.
 * 
 * @author rv
 */
public class DefaultResponseListener implements ResponseListener {
	public void returnCoin(Coin coin) {}
	public void vendItem(Selector selector) {}
}
