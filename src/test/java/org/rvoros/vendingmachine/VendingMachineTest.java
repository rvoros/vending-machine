package org.rvoros.vendingmachine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rvoros.vendingmachine.responses.ResponseListener;

/**
 * Unit tests for {@link VendingMachine}
 */
public class VendingMachineTest {

	/**
	 * The vending machine.
	 */
	private VendingMachine machine;
	
	private SimpleResponseListener responses;

	/**
	 * Simple implementation of {@link ResponseListener} for test.
	 */
	class SimpleResponseListener implements ResponseListener {
		private Selector selectorVended = null;
		private List<Coin> coinsReturned = new ArrayList<Coin>();

		public void vendItem(Selector selector) {
			this.selectorVended = selector;
		}
		
		public void returnCoin(Coin coin) {
			this.coinsReturned.add(coin);
		}

		public Selector getSelector() {
			return selectorVended;
		}

		public List<Coin> getCoins() {
			return coinsReturned;
		}
	};

	@Before
	public void setUp() throws Exception {
		machine = new VendingMachine();		

		responses = new SimpleResponseListener();
		machine.setResponseListener(responses);

		// fill up the machine with some items
		machine.addItem(new Item(Selector.A, 1, 0.6));
		machine.addItem(new Item(Selector.B, 1, 1));
		machine.addItem(new Item(Selector.C, 1, 1.7));

		// fill up the machine with some change
		machine.addChange(Coin.TEN_PENCE, 100);
		machine.addChange(Coin.TWENTY_PENCE, 100);
		machine.addChange(Coin.FIFTY_PENCE, 100);
		machine.addChange(Coin.ONE_POUND, 100);
	}

	// Test on/off state

	/**
	 * The defult state of the machine is off.
	 */
	@Test
	public void testDefaultStateIsOff() {
		assertFalse(machine.isOn());
	}

	/**
	 * After turning the machine on the state should be on.
	 */
	@Test
	public void testStateTurnOn(){
		machine.turnOn();
		assertTrue(machine.isOn());
	}

	/**
	 * After turning the machine off the state should be off
	 */
	@Test
	public void testStateTurnOff(){
		machine.turnOff();
		assertFalse(machine.isOn());
	}

	// Test coin insertion
	
	/**
	 * In case of OFF state every coins inserted by the customer should be returned immediately without pressing the coin return button.
	 */
	@Test
	public void testCoinInsertionInOffState(){
		machine.turnOff();
		// insert ten pence . This should not be stored in the machine because it is in OFF state.
		machine.insertMoney(Coin.TEN_PENCE);

		assertEquals(1, responses.getCoins().size());

		Coin coin = responses.getCoins().iterator().next();
		
		assertEquals(Coin.TEN_PENCE, coin);
	}

	/**
	 * In case of ON state the money should be returned only if the customer presses the return button.
	 */
	@Test
	public void testCoinInsertionInOnState(){
		machine.turnOn();
		// insert ten pence
		machine.insertMoney(Coin.TEN_PENCE);

		assertTrue(responses.getCoins().isEmpty());
		
		// when the user presses return money the machine should return exactly the inserted amount.
		machine.coinReturn();

		assertEquals(1, responses.getCoins().size());

		Coin coin = responses.getCoins().iterator().next();

		assertEquals(Coin.TEN_PENCE, coin);	
	}

	// Test coin return

	/**
	 * Right after turn on the machine should not have any inserted money.
	 */
	@Test
	public void testCoinReturnAfterTurnOn() {
		machine.turnOn();
		assertTrue(responses.getCoins().isEmpty());
	}

	/**
	 * Before turn off the machine should return all the inserted money to the customer.
	 */
	@Test
	public void testCoinReturnBeforeTurnOff() throws Exception {
		machine.turnOn();
		machine.insertMoney(Coin.TEN_PENCE);

		// before turn off the money should be returned to the customer.
		machine.turnOff();

		assertEquals(1, responses.getCoins().size());

		Coin coin = responses.getCoins().iterator().next();

		assertEquals(Coin.TEN_PENCE, coin);	
	}

	/**
	 * Test when the user press the return button.
	 * It should only return the inserted money ones!
	 */
	@Test
	public void testReturnButtonPress() {
		machine.turnOn();
		machine.insertMoney(Coin.TEN_PENCE);

		// the return button pressed by the customer
		machine.coinReturn();

		assertEquals(1, responses.getCoins().size());

		Coin coin = responses.getCoins().iterator().next();

		assertEquals(Coin.TEN_PENCE, coin);	
	}

	// Test vending
	
	/**
	 * Test buying an item of selector A. The inserted amount is exactly the same as the price of the item.
	 */
	@Test
	public void testBuyItemFromSelectorAWithoutChangeReturn() {
		machine.turnOn();
		machine.insertMoney(Coin.TEN_PENCE);
		machine.insertMoney(Coin.FIFTY_PENCE);

		machine.getA();
		
		assertNotNull(responses.getSelector());
		assertEquals(Selector.A, responses.getSelector());
		assertTrue(responses.getCoins().isEmpty());
	}

	/**
	 * Test buying an item of selector B. The inserted amount is exactly the same as the price of the item.
	 */
	@Test
	public void testBuyItemFromSelectorBWithoutChangeReturn() {
		machine.turnOn();
		machine.insertMoney(Coin.FIFTY_PENCE);
		machine.insertMoney(Coin.FIFTY_PENCE);

		machine.getB();
		
		assertNotNull(responses.getSelector());
		assertEquals(Selector.B, responses.getSelector());
		assertTrue(responses.getCoins().isEmpty());
	}

	/**
	 * Test buying an item of selector C. The inserted amount is exactly the same as the price of the item.
	 */
	@Test
	public void testBuyItemFromSelectorCWithoutChangeReturn() {
		machine.turnOn();
		machine.insertMoney(Coin.ONE_POUND);
		machine.insertMoney(Coin.FIFTY_PENCE);
		machine.insertMoney(Coin.TWENTY_PENCE);

		machine.getC();
		
		assertNotNull(responses.getSelector());
		assertEquals(Selector.C, responses.getSelector());
		assertTrue(responses.getCoins().isEmpty());
	}

	/**
	 * An item should not get sold if the inserted amount is less then the price of the item.
	 */
	@Test
	public void testInsertedMoneyIsLessThenTheItemsPrice() {
		machine.turnOn();
		machine.insertMoney(Coin.ONE_POUND);

		machine.getC();
		
		assertNull(responses.getSelector());
		assertTrue(responses.getCoins().isEmpty());
	}
	
	/**
	 * Test for getting the right change back in case more money is inserted than the price of the item.
	 */
	@Test
	public void testGettingChangeBack(){
		machine.turnOn();
		machine.insertMoney(Coin.ONE_POUND);
		machine.insertMoney(Coin.ONE_POUND);

		machine.getC();

		assertEquals(2, responses.getCoins().size());
		assertTrue(responses.getCoins().contains(Coin.TEN_PENCE));
		assertTrue(responses.getCoins().contains(Coin.TWENTY_PENCE));
	}
}
