package com.idugalic.apigateway;

import org.springframework.hateoas.ResourceSupport;

public class RootResource extends ResourceSupport {
    public static final String LINK_NAME_CATALOG = "catalog";
    public static final String LINK_NAME_ORDERS = "orders";
    public static final String LINK_NAME_REVIEW = "reviews";
    public static final String LINK_NAME_RECOMMENDATIONS = "recommendations";
    public static final String LINK_NAME_PRODUCT_DETAIL = "product";
}
