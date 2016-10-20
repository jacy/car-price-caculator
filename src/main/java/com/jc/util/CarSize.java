package com.jc.util;

import java.util.function.Predicate;

public enum CarSize {
	SMALL(cc -> cc > 0 && cc <= 2), MEDIEM(cc -> cc > 2 && cc <= 5), BIG(cc -> cc > 5);
	private Predicate<Double> ccRange;

	CarSize(Predicate<Double> ccRange) {
		this.ccRange = ccRange;
	}

	public Predicate<Double> getCcRange() {
		return ccRange;
	}
}