package com.westum.recommendations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.westum.recommendations.repositories.LikesRepository;
import com.westum.recommendations.repositories.PersonRepository;
import com.westum.recommendations.repositories.ProductRepository;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "com.westum.recommendations.repositories")
@EnableDiscoveryClient
@EnableOAuth2Resource
public class Application extends RepositoryRestMvcConfiguration implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    ProductRepository movieRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    LikesRepository likesRepository;

    @Override
    public void run(String... strings) throws Exception {
        movieRepository.deleteAll();
        personRepository.deleteAll();
        likesRepository.deleteAll();
    }
    @Configuration
	@EnableOAuth2Resource
	static class ResourceServer extends ResourceServerConfigurerAdapter {

	  @Override
	  public void configure(HttpSecurity http) throws Exception {
	    http.requestMatchers().and().authorizeRequests()
	     .antMatchers(HttpMethod.GET, "/recommendations/**").access("#oauth2.hasScope('read_recommendations') and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USER'))")
	     .antMatchers(HttpMethod.GET, "/products/**").access("#oauth2.hasScope('read_recommendations') and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USER'))")
	     .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write_recommendations') and hasRole('ROLE_ADMIN')")
	     .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write_recommendations') and hasRole('ROLE_ADMIN')")
	     .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write_recommendations') and hasRole('ROLE_ADMIN')")
	     
//	     .antMatchers("/health/**").anonymous()
//	     .antMatchers("/info/**").anonymous()
	     .antMatchers("/metrics/**").access("#oauth2.hasScope('metrics')")
         .antMatchers("/trace/**").access("#oauth2.hasScope('trace')")
         .antMatchers("/dump/**").access("#oauth2.hasScope('dump')")
         .antMatchers("/shutdown/**").access("#oauth2.hasScope('shutdown')")
         .antMatchers("/beans/**").access("#oauth2.hasScope('beans')")
         .antMatchers("/autoconfig/**").access("#oauth2.hasScope('autoconfig')")
		 .antMatchers("/configprops/**").access("#oauth2.hasScope('configprops')")
		 .antMatchers("/env/**").access("#oauth2.hasScope('env')")
		 .antMatchers("/mappings/**").access("#oauth2.hasScope('mappings')");
	     
	  }

	}


}
