package org.rvoros.vendingmachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rvoros.vendingmachine.responses.DefaultResponseListener;
import org.rvoros.vendingmachine.responses.ResponseListener;
import org.rvoros.vendingmachine.states.OffState;
import org.rvoros.vendingmachine.states.OnState;
import org.rvoros.vendingmachine.states.State;
import org.rvoros.vendingmachine.states.VendingState;

/**
 * Encapsulates the state of a vending machine and the operations that can be performed on it
 */
public class VendingMachine {
	public static final int OFF = 0;
	public static final int ON = 1;
	public static final int VENDING = 2;

	private State[] states = {new OffState(), new OnState(), new VendingState()};

	private int currentStateIdx = 0;
	
	private Map<Selector, Item> items = new HashMap<Selector, Item>();
	private Map<Coin, Integer> coins = new HashMap<Coin, Integer>();
	private List<Coin> insertedMoney = new ArrayList<Coin>();

	private ResponseListener responseListener = new DefaultResponseListener();
	
	public VendingMachine() {
		super();
	}

	public void setState(int stateIdx){
		this.currentStateIdx = stateIdx;
	}
	
	// Service methods

	public void addItem(Item item) {
		items.put(item.getSelector(), item);
	}

	public void addChange(Coin coin, int amount) {
		coins.put(coin, amount);
	}

	public void addToInsertedMoney(Coin coin) {
		insertedMoney.add(coin);
	}

	public List<Coin> getInsertedMoney() {
		return insertedMoney;
	}

	public Item getItemBySelector(Selector selector) {
		return items.get(selector);
	}

	public Map<Coin, Integer> getCoins() {
		return coins;
	}

	// Actions
	
	public void insertMoney(Coin coin) {
		states[currentStateIdx].insertMoney(this, coin);
	}

	public void coinReturn() {
		states[currentStateIdx].coinReturn(this);
	}

	public void getA() {
		states[currentStateIdx].selectItem(this, Selector.A);
	}

	public void getB() {
		states[currentStateIdx].selectItem(this, Selector.B);
	}

	public void getC() {
		states[currentStateIdx].selectItem(this, Selector.C);
	}

	public void turnOn() {
		states[currentStateIdx].turnOn(this);
	}
	
	public void turnOff() {
		states[currentStateIdx].turnOff(this);
	}

	// Responses

	public ResponseListener getResponseListener() {
		return this.responseListener;
	}

	public void setResponseListener(ResponseListener responseListener) {
		this.responseListener = responseListener;
	}

	// State

	public boolean isOn(){
		return currentStateIdx != OFF;
	}
}
