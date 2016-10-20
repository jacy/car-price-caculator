package com.jc.util;

import static org.junit.Assert.*;
import static com.jc.util.Country.*;
import static com.jc.util.CarCustomOffice.roundDown;

import java.math.BigDecimal;

import org.junit.Test;

public class CarPriceCaculatorTest {

	@Test
	public void testPrinceInUSD() {
		assertEquals(roundDown(732144.00), CarPriceCaculator.priceInUSD(new CarItem(EUROPE, BigDecimal.valueOf(217900), 6)));
		assertEquals(roundDown(37108.96), CarPriceCaculator.priceInUSD(new CarItem(JAPAN, BigDecimal.valueOf(19490), 1.5)));
		assertEquals(roundDown(78725.36), CarPriceCaculator.priceInUSD(new CarItem(USA, BigDecimal.valueOf(36995), 3.6)));
		try {
			CarPriceCaculator.priceInUSD(new CarItem(CHINA, BigDecimal.valueOf(6000), 1));
		} catch (RuntimeException e) {
			assertEquals("CHINA not support", e.getMessage());
		}
	}

	@Test
	public void testPrinceInPHP() {
		assertEquals(roundDown(34410768), CarPriceCaculator.priceInPhp(new CarItem(EUROPE, BigDecimal.valueOf(217900), 6)));
		assertEquals(roundDown(1744121.12), CarPriceCaculator.priceInPhp(new CarItem(JAPAN, BigDecimal.valueOf(19490), 1.5)));
		assertEquals(roundDown(3700091.92), CarPriceCaculator.priceInPhp(new CarItem(USA, BigDecimal.valueOf(36995), 3.6)));
		try {
			CarPriceCaculator.priceInPhp(new CarItem(CHINA, BigDecimal.valueOf(6000), 1));
		} catch (RuntimeException e) {
			assertEquals("CHINA not support", e.getMessage());
		}
	}

	@Test
	public void testUsdToPhp() {
		assertEquals(roundDown(47.00), CarPriceCaculator.usd2php(BigDecimal.valueOf(1)));
		assertEquals(roundDown(57.99), CarPriceCaculator.usd2php(BigDecimal.valueOf(1.234)));
	}

}
