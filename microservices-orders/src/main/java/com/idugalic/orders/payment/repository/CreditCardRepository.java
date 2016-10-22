package com.idugalic.orders.payment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idugalic.orders.payment.domain.CreditCard;
import com.idugalic.orders.payment.domain.CreditCardNumber;

/**
 * Repository to access {@link CreditCard} instances.
 * 
 */
@RepositoryRestResource(exported = false)
public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {

	/**
	 * Returns the {@link CreditCard} assicaiated with the given
	 * {@link CreditCardNumber}.
	 * 
	 * @param number
	 *            must not be {@literal null}.
	 * @return
	 */
	CreditCard findByNumber(CreditCardNumber number);
}
