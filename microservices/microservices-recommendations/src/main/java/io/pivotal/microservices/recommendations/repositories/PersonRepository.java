package io.pivotal.microservices.recommendations.repositories;

import io.pivotal.microservices.recommendations.model.Person;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface PersonRepository extends GraphRepository<Person> {
    Person findByUserName(String userName);
}
