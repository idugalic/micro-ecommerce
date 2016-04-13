package com.idugalic.apigateway;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.cloud.security.oauth2.sso.OAuth2SsoConfigurer;
import org.springframework.cloud.security.oauth2.sso.OAuth2SsoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.util.WebUtils;

import com.idugalic.apigateway.models.ProductDetails;
import com.idugalic.apigateway.services.catalog.CatalogIntegrationService;
import com.idugalic.apigateway.services.recommendations.RecommendationsIntegrationService;
import com.idugalic.apigateway.services.reviews.ReviewsIntegrationService;

import rx.Observable;
import rx.Observer;

@SpringBootApplication
@EnableZuulProxy
@RestController
@EnableHypermediaSupport(type = { HypermediaType.HAL })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    protected static class SecurityConfiguration extends OAuth2SsoConfigurerAdapter {

        @Override
        public void match(OAuth2SsoConfigurer.RequestMatchers matchers) {
            matchers.anyRequest();
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.logout().and().antMatcher("/**").authorizeRequests()
                    .antMatchers("/browser/**","/index.html", "/home.html", "/", "/login", "/beans").permitAll()
                    .antMatchers(HttpMethod.GET, "/recommendations/**","/reviews/**","/product/**","/catalog/**","/orders/**").permitAll()
                    .anyRequest().authenticated().and().csrf()
                    .csrfTokenRepository(csrfTokenRepository()).and()
                    .addFilterBefore(new RequestContextFilter(), HeaderWriterFilter.class)
                    .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
        }

        private Filter csrfHeaderFilter() {
            return new OncePerRequestFilter() {
                @Override
                protected void doFilterInternal(HttpServletRequest request,
                                                HttpServletResponse response, FilterChain filterChain)
                        throws ServletException, IOException {
                    CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                            .getName());
                    if (csrf != null) {
                        Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                        String token = csrf.getToken();
                        if (cookie == null || token != null
                                && !token.equals(cookie.getValue())) {
                            cookie = new Cookie("XSRF-TOKEN", token);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                    }
                    filterChain.doFilter(request, response);
                }
            };
        }

        private CsrfTokenRepository csrfTokenRepository() {
            HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
            repository.setHeaderName("X-XSRF-TOKEN");
            return repository;
        }
    }
    
    @Autowired
    CatalogIntegrationService catalogIntegrationService;

    @Autowired
    ReviewsIntegrationService reviewsIntegrationService;

    @Autowired
    RecommendationsIntegrationService recommendationsIntegrationService;
   
    @Configuration
    protected static class TestConfiguration extends OAuth2SsoConfigurerAdapter {
        @Override
        public void match(RequestMatchers matchers) {
            matchers.antMatchers("/**");
        }
    }
    @RequestMapping("/")
    public HttpEntity<RootResource> getRootResource() {
    	 RootResource resource = new RootResource();
    	 String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
         
         Link catalogServiceLink = new Link(
                 new UriTemplate(baseUri + "/catalog"), RootResource.LINK_NAME_CATALOG);
         resource.add(catalogServiceLink);
         
         Link reviewServiceLink = new Link(
                 new UriTemplate(baseUri + "/reviews"), RootResource.LINK_NAME_REVIEW);
         resource.add(reviewServiceLink);
         
         Link recommendationServiceLink = new Link(
                 new UriTemplate(baseUri + "/recommendations"), RootResource.LINK_NAME_RECOMMENDATIONS);
         resource.add(recommendationServiceLink);
         
         Link orderServiceLink = new Link(
                 new UriTemplate(baseUri + "/orders"), RootResource.LINK_NAME_ORDERS);
         resource.add(orderServiceLink);
         
         Link productLink = new Link(
                 new UriTemplate(baseUri + "/product/{id}"), RootResource.LINK_NAME_PRODUCT_DETAIL);
         resource.add(productLink);
         
         
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping("/product/{productId}")
    public DeferredResult<ProductDetails> productDetails(@PathVariable String productId , HttpServletRequest request) {
    	String token = request.getHeader("authorization");
        Observable<ProductDetails> details = Observable.zip(
                catalogIntegrationService.getProduct(productId, token),
                reviewsIntegrationService.reviewsFor(productId, token),
                recommendationsIntegrationService.getRecommendations(productId, token),
                (product, reviews, recommendations) -> {
                    ProductDetails productDetails = new ProductDetails();
                    productDetails.setProductId(String.valueOf(product.getId()));
                    productDetails.setName(product.getName());
                    productDetails.setReviews(reviews);
                    productDetails.setRecommendations(recommendations);
                    return productDetails;
                }
        );
        return toDeferredResult(details);
    }

    public DeferredResult<ProductDetails> toDeferredResult(Observable<ProductDetails> details) {
        DeferredResult<ProductDetails> result = new DeferredResult<>();
        details.subscribe(new Observer<ProductDetails>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(ProductDetails productDetails) {
                result.setResult(productDetails);
            }
        });
        return result;
    }
    
    @Configuration
	@EnableOAuth2Resource
	static class ResourceServer extends ResourceServerConfigurerAdapter {

	  @Override
	  public void configure(HttpSecurity http) throws Exception {
	    http.requestMatchers().and().authorizeRequests()
	     .antMatchers(HttpMethod.GET, "/reviews/**").authenticated()
	     .antMatchers(HttpMethod.GET, "/catalog/**").authenticated()
	     .antMatchers(HttpMethod.GET, "/recommendations/**").authenticated()
	     .antMatchers(HttpMethod.GET, "/orders/**").authenticated()
	     .antMatchers(HttpMethod.GET, "/product/**").authenticated()
	     

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
