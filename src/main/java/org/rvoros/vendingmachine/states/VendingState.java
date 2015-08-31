package org.rvoros.vendingmachine.states;

import java.util.List;
import java.util.Map;

import org.rvoros.vendingmachine.Coin;
import org.rvoros.vendingmachine.Item;
import org.rvoros.vendingmachine.Selector;
import org.rvoros.vendingmachine.VendingMachine;

public class VendingState implements State {

	public void insertMoney(VendingMachine vendingMachine, Coin coin) {
		vendingMachine.addToInsertedMoney(coin);
	}

	public void selectItem(VendingMachine vendingMachine, Selector selector) {
		Item item = vendingMachine.getItemBySelector(selector);

		if(item != null && item.notEmpty()){
			final List<Coin> insertedMoney = vendingMachine.getInsertedMoney();
			
			double sum = 0;
			for (Coin coin : insertedMoney) {
				sum += coin.getValue();
			}

			if(sum >= item.getPrice()){
				item.decreaseAmount();
				vendingMachine.getResponseListener().vendItem(selector);

				for (Coin coin : insertedMoney) {
					vendingMachine.addChange(coin, 1);
				}

				if(sum > item.getPrice()){
					double changeToReturn = sum - item.getPrice();
					
					Map<Coin, Integer> coins = vendingMachine.getCoins();
					
					int onePound = (int) (changeToReturn / Coin.ONE_POUND.getValue());
					onePound = Math.min(onePound, coins.get(Coin.ONE_POUND));

					changeToReturn -= (onePound * Coin.ONE_POUND.getValue());
					int fifty = (int) (changeToReturn / Coin.FIFTY_PENCE.getValue());
					fifty = Math.min(fifty, coins.get(Coin.FIFTY_PENCE));
					
					changeToReturn -= (fifty * Coin.FIFTY_PENCE.getValue());
					int twenty = (int) (changeToReturn / Coin.TWENTY_PENCE.getValue());
					twenty = Math.min(twenty, coins.get(Coin.TWENTY_PENCE));

					changeToReturn -= (twenty * Coin.TWENTY_PENCE.getValue());
					int ten = (int) (changeToReturn / Coin.TEN_PENCE.getValue());
					ten = Math.min(ten, coins.get(Coin.TEN_PENCE));
					
					if(onePound > 0){
						coins.put(Coin.ONE_POUND, coins.get(Coin.ONE_POUND) - onePound);
						for (int i = 0; i < onePound; i++) {							
							vendingMachine.getResponseListener().returnCoin(Coin.ONE_POUND);
						}
					}
					if(fifty > 0){
						coins.put(Coin.FIFTY_PENCE, coins.get(Coin.FIFTY_PENCE) - fifty);
						for (int i = 0; i < fifty; i++) {							
							vendingMachine.getResponseListener().returnCoin(Coin.FIFTY_PENCE);
						}
					}
					if(twenty > 0){
						coins.put(Coin.TWENTY_PENCE, coins.get(Coin.TWENTY_PENCE) - twenty);
						for (int i = 0; i < twenty; i++) {							
							vendingMachine.getResponseListener().returnCoin(Coin.TWENTY_PENCE);
						}
					}
					if(ten > 0){
						coins.put(Coin.TEN_PENCE, coins.get(Coin.TEN_PENCE) - ten);
						for (int i = 0; i < ten; i++) {							
							vendingMachine.getResponseListener().returnCoin(Coin.TEN_PENCE);
						}
					}
				}
			}
		}
	}

	public void turnOn(VendingMachine vendingMachine) {
		// we are already in state ON
	}

	public void turnOff(VendingMachine vendingMachine) {
		coinReturn(vendingMachine);
		vendingMachine.setState(VendingMachine.OFF);
	}

	public void coinReturn(VendingMachine vendingMachine) {
		for (Coin coin : vendingMachine.getInsertedMoney()) {
			vendingMachine.getResponseListener().returnCoin(coin);
		}
		vendingMachine.getInsertedMoney().clear();
	}
}
