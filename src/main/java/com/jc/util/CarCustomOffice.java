package com.jc.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static com.jc.util.CarSize.*;
import static com.jc.util.Country.*;

public class CarCustomOffice {
	private static Map<TaxKey, BigDecimal> rateMaps = new HashMap<TaxKey, BigDecimal>();
	static {
		rateMaps.put(key(EUROPE, SMALL), BigDecimal.ONE);
		rateMaps.put(key(EUROPE, MEDIEM), BigDecimal.valueOf(1.2));
		rateMaps.put(key(EUROPE, BIG), BigDecimal.valueOf(2));
		rateMaps.put(key(USA, SMALL), BigDecimal.valueOf(0.75));
		rateMaps.put(key(USA, MEDIEM), BigDecimal.valueOf(0.9));
		rateMaps.put(key(USA, BIG), BigDecimal.valueOf(1.5));
		rateMaps.put(key(JAPAN, SMALL), BigDecimal.valueOf(0.7));
		rateMaps.put(key(JAPAN, MEDIEM), BigDecimal.valueOf(0.8));
		rateMaps.put(key(JAPAN, BIG), BigDecimal.valueOf(1.35));
	}

	public static BigDecimal caculateTax(CarItem item) {
		CarSize size = determinSize(item.getCc());
		BigDecimal taxRate = getTaxRate(item.getCountry(), size);
		return roundDown(item.getPrice().multiply(taxRate));
	}
	
	public static BigDecimal roundDown(BigDecimal num){
		return num.setScale(2,RoundingMode.DOWN);
	}
	public static BigDecimal roundDown(double num){
		return roundDown(BigDecimal.valueOf(num));
	}

	public static BigDecimal getTaxRate(Country country, CarSize carSize) {
		return Optional.ofNullable(rateMaps.get(key(country, carSize))).orElseThrow(error(country + " not support"));
	}

	public static CarSize determinSize(double cc) {
		return Arrays.stream(CarSize.values()).filter(size -> size.getCcRange().test(cc)).findFirst().orElseThrow(error("Displacement is out of range"));
	}

	public static Supplier<? extends RuntimeException> error(String errorMessage) {
		return () -> new RuntimeException(errorMessage);
	}

	public static TaxKey key(Country country, CarSize carSize) {
		return new TaxKey(country, carSize);
	}
}
