package com.jc.util;

public class TaxKey {
	private Country country;
	private CarSize carSize;

	public TaxKey(Country country, CarSize carSize) {
		this.country = country;
		this.carSize = carSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carSize == null) ? 0 : carSize.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaxKey other = (TaxKey) obj;
		if (carSize != other.carSize)
			return false;
		if (country != other.country)
			return false;
		return true;
	}

}
