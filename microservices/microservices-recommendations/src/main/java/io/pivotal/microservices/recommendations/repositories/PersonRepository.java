package io.pivotal.microservices.recommendations.repositories;

import io.pivotal.microservices.recommendations.model.Person;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends GraphRepository<Person> {
	@Query("MATCH (person:Person) WHERE person.userName = {userName} RETURN person")
	Person findByUserName(@Param("userName") String userName);
}
