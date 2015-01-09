package com.westum.catalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {

    @Autowired
 

    //This controller is constructed for this method only. Enables users to create likes much easier.
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody String test() {
    	Object pricipal = null;
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if (auth != null)
    		pricipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    	System.out.println(auth.getName());
      

        return "Test";
    }

}
