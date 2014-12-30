package com.westum.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.westum.catalog.models.Category;
import com.westum.catalog.models.Product;

@SpringBootApplication
@EnableJpaRepositories
@EnableDiscoveryClient
public class Application

{
	public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	    }
	
	@Configuration
	static class RestMvc extends RepositoryRestMvcConfiguration {
	
		@Override
		protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
			config.exposeIdsFor(Product.class, Category.class);
		}
		
	   
	   
	}
	@Configuration
	@EnableOAuth2Resource
	static class ResourceServer extends ResourceServerConfigurerAdapter {

	  @Override
	  public void configure(HttpSecurity http) throws Exception {
	    http.requestMatchers()
	      .antMatchers("/**")
	   .and()
	     .authorizeRequests()
	       .anyRequest().authenticated();
	  }

	}

}
