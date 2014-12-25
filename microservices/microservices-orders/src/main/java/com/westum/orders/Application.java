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
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;

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

	
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}
}
