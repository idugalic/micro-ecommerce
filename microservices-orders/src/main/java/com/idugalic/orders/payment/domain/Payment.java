package com.idugalic.orders.payment.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;




import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import com.idugalic.orders.order.domain.Order;


/**
 * Baseclass for payment implementations.
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Payment{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;
	
	@JoinColumn(name = "rborder")//
	@OneToOne(cascade = CascadeType.MERGE)//
	private Order order;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")//
	private DateTime paymentDate;

	public Payment() {
		super();
	}

	/**
	 * Creates a new {@link Payment} referring to the given {@link Order}.
	 * 
	 * @param order must not be {@literal null}.
	 */
	public Payment(Order order) {

		Assert.notNull(order);
		this.order = order;
		this.paymentDate = new DateTime();
	}

	/**
	 * Returns a receipt for the {@link Payment}.
	 * 
	 * @return
	 */
	public Receipt getReceipt() {
		return new Receipt(order, paymentDate);
	}

	/**
	 * A receipt for an {@link Order} and a payment date.
	 * 
	 */
	
	public static class Receipt {

		private  Order order;
		private  DateTime date;
		public Receipt(Order order, DateTime date) {
			super();
			this.order = order;
			this.date = date;
		}
		public Receipt() {
			super();
		}
		public Order getOrder() {
			return order;
		}
		public void setOrder(Order order) {
			this.order = order;
		}
		public DateTime getDate() {
			return date;
		}
		public void setDate(DateTime date) {
			this.date = date;
		}
		
		
		
	}
}
