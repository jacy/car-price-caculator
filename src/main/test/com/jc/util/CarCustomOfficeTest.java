package com.jc.util;

import static com.jc.util.CarCustomOffice.caculateTax;
import static com.jc.util.CarCustomOffice.determinSize;
import static com.jc.util.CarCustomOffice.error;
import static com.jc.util.CarCustomOffice.getTaxRate;
import static com.jc.util.CarCustomOffice.key;
import static com.jc.util.CarCustomOffice.roundDown;
import static com.jc.util.CarSize.*;
import static com.jc.util.Country.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.function.Supplier;

import org.junit.Test;

public class CarCustomOfficeTest {

	@Test
	public void testCaculateTaxRate() {
		assertEquals(roundDown(99.88), caculateTax(new CarItem(EUROPE, BigDecimal.valueOf(99.8812), 1)));
		assertEquals(roundDown(1), caculateTax(new CarItem(EUROPE, BigDecimal.valueOf(1), 1)));
		assertEquals(roundDown(119.85), caculateTax(new CarItem(EUROPE, BigDecimal.valueOf(99.8812), 2.5)));
		assertEquals(roundDown(1.2), caculateTax(new CarItem(EUROPE, BigDecimal.valueOf(1), 2.5)));
		assertEquals(roundDown(199.76), caculateTax(new CarItem(EUROPE, BigDecimal.valueOf(99.8812), 5.1)));
		assertEquals(roundDown(2), caculateTax(new CarItem(EUROPE, BigDecimal.valueOf(1), 99)));

		assertEquals(roundDown(69.91), caculateTax(new CarItem(JAPAN, BigDecimal.valueOf(99.8812), 1)));
		assertEquals(roundDown(0.7), caculateTax(new CarItem(JAPAN, BigDecimal.valueOf(1), 1)));
		assertEquals(roundDown(79.90), caculateTax(new CarItem(JAPAN, BigDecimal.valueOf(99.8812), 2.5)));
		assertEquals(roundDown(0.8), caculateTax(new CarItem(JAPAN, BigDecimal.valueOf(1), 2.5)));
		assertEquals(roundDown(134.83), caculateTax(new CarItem(JAPAN, BigDecimal.valueOf(99.8812), 5.1)));
		assertEquals(roundDown(1.35), caculateTax(new CarItem(JAPAN, BigDecimal.valueOf(1), 99)));

		assertEquals(roundDown(74.91), caculateTax(new CarItem(USA, BigDecimal.valueOf(99.8812), 1)));
		assertEquals(roundDown(0.75), caculateTax(new CarItem(USA, BigDecimal.valueOf(1), 1)));
		assertEquals(roundDown(89.89), caculateTax(new CarItem(USA, BigDecimal.valueOf(99.8812), 2.5)));
		assertEquals(roundDown(0.9), caculateTax(new CarItem(USA, BigDecimal.valueOf(1), 2.5)));
		assertEquals(roundDown(149.82), caculateTax(new CarItem(USA, BigDecimal.valueOf(99.8812), 5.1)));
		assertEquals(roundDown(1.5), caculateTax(new CarItem(USA, BigDecimal.valueOf(1), 99)));

		try {
			caculateTax(new CarItem(EUROPE, BigDecimal.valueOf(1), 0));
		} catch (RuntimeException e) {
			assertEquals("Displacement is out of range", e.getMessage());
		}
	}

	@Test
	public void testDeterminSizeShouldThrowExceptionIfCCIsInvalid() {
		try {
			determinSize(0);
			fail();
		} catch (RuntimeException e) {
			assertEquals("Displacement is out of range", e.getMessage());
		}
		try {
			CarCustomOffice.determinSize(-1);
			fail();
		} catch (RuntimeException e) {
			assertEquals("Displacement is out of range", e.getMessage());
		}
	}

	@Test
	public void testGetTaxRateThrowExceptionIfTaxKeyNotFound() {
		try {
			getTaxRate(null, SMALL);
			fail();
		} catch (RuntimeException e) {
			assertEquals("null not support", e.getMessage());
		}
		try {
			getTaxRate(CHINA, null);
			fail();
		} catch (RuntimeException e) {
			assertEquals("CHINA not support", e.getMessage());
		}
	}

	@Test
	public void testGetTaxRateSuccess() {
		assertEquals(BigDecimal.valueOf(1), getTaxRate(EUROPE, SMALL));
		assertEquals(BigDecimal.valueOf(1.2), getTaxRate(EUROPE, MEDIEM));
		assertEquals(BigDecimal.valueOf(2), getTaxRate(EUROPE, BIG));
		assertEquals(BigDecimal.valueOf(0.75), getTaxRate(USA, SMALL));
		assertEquals(BigDecimal.valueOf(0.9), getTaxRate(USA, MEDIEM));
		assertEquals(BigDecimal.valueOf(1.5), getTaxRate(USA, BIG));
		assertEquals(BigDecimal.valueOf(0.7), getTaxRate(JAPAN, SMALL));
		assertEquals(BigDecimal.valueOf(0.8), getTaxRate(JAPAN, MEDIEM));
		assertEquals(BigDecimal.valueOf(1.35), getTaxRate(JAPAN, BIG));
	}

	@Test
	public void testDeterminSizeShouldBeAbleToTellTheSize() {
		assertEquals(SMALL, determinSize(0.00001234));
		assertEquals(SMALL, determinSize(0.00000001));
		assertEquals(SMALL, determinSize(1.99999999999999999));
		assertEquals(SMALL, determinSize(1));
		assertEquals(SMALL, determinSize(2));
		assertEquals(SMALL, determinSize(2.0000000000));
		assertEquals(SMALL, determinSize(2.000));

		assertEquals(MEDIEM, determinSize(2.00000001));
		assertEquals(MEDIEM, determinSize(3));
		assertEquals(MEDIEM, determinSize(4));
		assertEquals(MEDIEM, determinSize(5));
		assertEquals(MEDIEM, determinSize(5.00000000));
		assertEquals(MEDIEM, determinSize(5.0000));

		assertEquals(BIG, determinSize(5.0000001));
		assertEquals(BIG, determinSize(5.00010001));
		assertEquals(BIG, determinSize(6));
		assertEquals(BIG, determinSize(7));
		assertEquals(BIG, determinSize(8));
		assertEquals(BIG, determinSize(Double.MAX_VALUE));
	}

	@Test
	public void testErrorMaker() {
		String errorMessage = "error test";
		Supplier<RuntimeException> em = () -> new RuntimeException(errorMessage);
		assertEquals(em.get().getMessage(), error(errorMessage).get().getMessage());
	}

	@Test
	public void testTaxKeyMaker() {
		assertEquals(new TaxKey(EUROPE, BIG), key(EUROPE, BIG));
		assertEquals(new TaxKey(JAPAN, MEDIEM), key(JAPAN, MEDIEM));
	}

}
