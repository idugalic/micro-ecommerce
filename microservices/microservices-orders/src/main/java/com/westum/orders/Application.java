package com.westum.orders;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.westum.orders.order.domain.Order;
import com.westum.orders.payment.domain.Payment;

@SpringBootApplication
@EnableJpaRepositories
@EnableDiscoveryClient
@EnableAsync
public class Application extends RepositoryRestMvcConfiguration {
	
	@Override
	protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Order.class, Payment.class);
	}
	
	// CORS
	@Bean
	FilterRegistrationBean corsFilter(@Value("${com.westum.orders.origin:}") String origin) {
		return new FilterRegistrationBean(new Filter() {
			public void doFilter(ServletRequest req, ServletResponse res,
					FilterChain chain) throws IOException, ServletException {
				HttpServletRequest request = (HttpServletRequest) req;
				HttpServletResponse response = (HttpServletResponse) res;
				String method = request.getMethod();
				response.setHeader("Access-Control-Allow-Origin",
						"http://localhost:8080");
				response.setHeader("Access-Control-Allow-Methods",
						"POST,GET,OPTIONS,DELETE");
				response.setHeader("Access-Control-Max-Age",
						Long.toString(60 * 60));
				response.setHeader("Access-Control-Allow-Credentials", "true");
				response.setHeader(
						"Access-Control-Allow-Headers",
						"Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
				if ("OPTIONS".equals(method)) {
					response.setStatus(HttpStatus.OK.value());
				} else {
					chain.doFilter(req, res);
				}
			}

			public void init(FilterConfig filterConfig) {
			}

			public void destroy() {
			}
		});
	}
	
	@Configuration
	@EnableOAuth2Resource
	static class ResourceServer extends ResourceServerConfigurerAdapter {

	  @Override
	  public void configure(HttpSecurity http) throws Exception {
	    http.requestMatchers().and().authorizeRequests()
	     .antMatchers(HttpMethod.GET, "/orders/**").access("#oauth2.hasScope('read_orders') and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USER'))")
	     .antMatchers(HttpMethod.GET, "/engine/**").access("#oauth2.hasScope('read_orders') and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USER'))")
	     .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write_orders') and hasRole('ROLE_ADMIN')")
	     .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write_orders') and hasRole('ROLE_ADMIN')")
	     .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write_orders') and hasRole('ROLE_ADMIN')")
	     
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


	
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}
}
