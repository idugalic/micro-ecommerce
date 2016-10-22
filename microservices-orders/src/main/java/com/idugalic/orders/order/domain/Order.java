package com.idugalic.orders.order.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.hateoas.Identifiable;

@Entity
@Table(name = "WOrder")
public class Order implements Identifiable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	private Status status;
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime orderedDate;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Item> items = new HashSet<Item>();

	public Order(Collection<Item> items) {
		this.status = Status.PAYMENT_EXPECTED;
		this.items.addAll(items);
		this.orderedDate = new DateTime();
	}

	public Order(Item... items) {
		this(Arrays.asList(items));
	}

	public Order() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public MonetaryAmount getPrice() {
		MonetaryAmount result = MonetaryAmount.ZERO;

		for (Item item : items) {
			result = result.add(item.getPrice());
		}

		return result;
	}

	public void markPaid() {

		if (isPaid()) {
			throw new IllegalStateException("Already paid order cannot be paid again!");
		}

		this.status = Status.PAID;
	}

	public void markInPreparation() {

		if (this.status != Status.PAID) {
			throw new IllegalStateException(String
					.format("Order must be in state payed to start preparation! " + "Current status: %s", this.status));
		}

		this.status = Status.PREPARING;
	}

	public void markPrepared() {

		if (this.status != Status.PREPARING) {
			throw new IllegalStateException(String.format(
					"Cannot mark Order prepared that is currently not " + "preparing! Current status: %s.",
					this.status));
		}

		this.status = Status.READY;
	}

	public boolean isPaid() {
		return !this.status.equals(Status.PAYMENT_EXPECTED);
	}

	public boolean isReady() {
		return this.status.equals(Status.READY);
	}

	public boolean isTaken() {
		return this.status.equals(Status.TAKEN);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public DateTime getOrderedDate() {
		return orderedDate;
	}

	public Set<Item> getItems() {
		return items;
	}

	public static enum Status {

		/**
		 * Placed, but not payed yet. Still changeable.
		 */
		PAYMENT_EXPECTED,

		/**
		 * {@link Order} was payed. No changes allowed to it anymore.
		 */
		PAID,

		/**
		 * The {@link Order} is currently processed.
		 */
		PREPARING,

		/**
		 * The {@link Order} is ready to be picked up by the customer.
		 */
		READY,

		/**
		 * The {@link Order} was completed.
		 */
		TAKEN;
	}
}
