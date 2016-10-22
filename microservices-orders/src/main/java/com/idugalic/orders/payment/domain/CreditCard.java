package com.idugalic.orders.payment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Years;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * Abstraction of a credit card.
 * 
 */
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	@JsonUnwrapped
	private CreditCardNumber number;

	private String cardHolderName;

	private Months expiryMonth;
	private Years expiryYear;

	public CreditCard(CreditCardNumber number, String cardHolderName, Months expiryMonth, Years expiryYear) {
		super();
		this.number = number;
		this.cardHolderName = cardHolderName;
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
	}

	/**
	 * Returns whether the {@link CreditCard} is currently valid.
	 * 
	 * @return
	 */
	@JsonIgnore
	public boolean isValid() {
		return isValid(new LocalDate());
	}

	/**
	 * Returns whether the {@link CreditCard} is valid for the given date.
	 * 
	 * @param date
	 * @return
	 */
	public boolean isValid(LocalDate date) {
		return date == null ? false : getExpirationDate().isAfter(date);
	}

	/**
	 * Returns the {@link LocalDate} the {@link CreditCard} expires.
	 * 
	 * @return will never be {@literal null}.
	 */
	public LocalDate getExpirationDate() {
		return new LocalDate(expiryYear.getYears(), expiryMonth.getMonths(), 1);
	}

	/**
	 * Protected setter to allow binding the expiration date.
	 * 
	 * @param date
	 */
	protected void setExpirationDate(LocalDate date) {

		this.expiryYear = Years.years(date.getYear());
		this.expiryMonth = Months.months(date.getMonthOfYear());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public CreditCardNumber getNumber() {
		return number;
	}

	public void setNumber(CreditCardNumber number) {
		this.number = number;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

}
