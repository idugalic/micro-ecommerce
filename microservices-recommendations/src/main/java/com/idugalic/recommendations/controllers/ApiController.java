package com.idugalic.recommendations.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idugalic.recommendations.model.Likes;
import com.idugalic.recommendations.model.Person;
import com.idugalic.recommendations.model.Product;
import com.idugalic.recommendations.repositories.LikesRepository;
import com.idugalic.recommendations.repositories.PersonRepository;
import com.idugalic.recommendations.repositories.ProductRepository;

@RestController
@ExposesResourceFor(Likes.class)
public class ApiController {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	LikesRepository likesRepository;

	// This controller is constructed for this method only. Enables users to
	// create likes much easier.
	@RequestMapping(value = "/recommendations/{userName}/likes/{productId}", method = RequestMethod.POST)
	public ResponseEntity<Likes> createPersonProductLink(@PathVariable String userName,
			@PathVariable String productId) {
		Person person = personRepository.findByUserName(userName);
		Product product = productRepository.findByProductId(productId);

		Likes likes = new Likes();
		likes.setPerson(person);
		likes.setProduct(product);
		likesRepository.save(likes);

		return new ResponseEntity<>(likes, HttpStatus.CREATED);
	}

}
