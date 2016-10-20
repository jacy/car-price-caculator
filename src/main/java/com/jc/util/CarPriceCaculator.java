package com.jc.util;

import java.math.BigDecimal;

import static com.jc.util.CarCustomOffice.roundDown;

public class CarPriceCaculator {
	private static final BigDecimal VAT_RATE = BigDecimal.valueOf(0.12);
	private static final BigDecimal USD2PHP = BigDecimal.valueOf(47);;

	public static BigDecimal priceInUSD(CarItem car) {
		BigDecimal tax = CarCustomOffice.caculateTax(car);
		BigDecimal pricewithTax = car.getPrice().add(tax);
		BigDecimal VAT = pricewithTax.multiply(VAT_RATE);
		return roundDown(pricewithTax.add(VAT));
	}

	public static BigDecimal priceInPhp(CarItem car) {
		BigDecimal usdPrice = priceInUSD(car);
		return usd2php(usdPrice);
	}

	public static BigDecimal usd2php(BigDecimal usd) {
		return roundDown(usd.multiply(USD2PHP));
	}
}