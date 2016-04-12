package com.westum.orders.order.domain;

import java.math.BigDecimal;
import java.util.Currency;

import javax.persistence.Embeddable;


import org.springframework.util.Assert;

@Embeddable
public class MonetaryAmount {

	public static Currency EURO = Currency.getInstance("EUR");
	public static MonetaryAmount ZERO = new MonetaryAmount();
	private final Currency currency;
	private final BigDecimal value;

	public MonetaryAmount(Currency currency, double value) {
		this(currency, new BigDecimal(value));
	}
	private MonetaryAmount(Currency currency, BigDecimal value) {

		Assert.notNull(currency);
		Assert.notNull(value);

		this.currency = currency;
		this.value = value;
	}

	protected MonetaryAmount() {
		this(EURO, BigDecimal.ZERO);
	}

	@Override
	public String toString() {
		return currency + value.toString();
	}

	public MonetaryAmount add(MonetaryAmount other) {

		if (other == null) {
			return this;
		}

		Assert.isTrue(this.currency.equals(other.currency));
		return new MonetaryAmount(this.currency, this.value.add(other.value));
	}
	public Currency getCurrency() {
		return currency;
	}
	public BigDecimal getValue() {
		return value;
	}
	
}
