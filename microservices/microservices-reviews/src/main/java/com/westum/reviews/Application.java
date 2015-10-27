package com.westum.reviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.westum.reviews.models.Review;

@SpringBootApplication
@EnableMongoRepositories
@EnableDiscoveryClient
public class Application extends RepositoryRestMvcConfiguration{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

   
    @Override
   	protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
   		config.exposeIdsFor(Review.class);
   	}
    
    @Configuration
	@EnableOAuth2Resource
	static class ResourceServer extends ResourceServerConfigurerAdapter {

	  @Override
	  public void configure(HttpSecurity http) throws Exception {
	    http.requestMatchers().and().authorizeRequests()
	     .antMatchers(HttpMethod.GET, "/reviews/**").access("#oauth2.hasScope('read_reviews') and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USER'))")
	     .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write_reviews') and hasRole('ROLE_ADMIN')")
	     .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write_reviews') and hasRole('ROLE_ADMIN')")
	     .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write_reviews') and hasRole('ROLE_ADMIN')")
	     
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
