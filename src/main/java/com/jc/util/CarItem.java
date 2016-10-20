package com.jc.util;

import java.math.BigDecimal;

public class CarItem {
	private Country country;
	private BigDecimal price;
	private double cc;

	public CarItem(Country country, BigDecimal price, double cc) {
		this.country = country;
		this.price = price;
		this.cc = cc;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Country getCountry() {
		return country;
	}

	public double getCc() {
		return cc;
	}

}
