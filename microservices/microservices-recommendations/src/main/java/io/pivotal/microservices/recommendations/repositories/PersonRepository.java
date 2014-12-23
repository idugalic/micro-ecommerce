package io.pivotal.microservices.recommendations.repositories;

import io.pivotal.microservices.recommendations.model.Person;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends GraphRepository<Person> {
    Person findByUserName(@Param("0") String userName);
}
