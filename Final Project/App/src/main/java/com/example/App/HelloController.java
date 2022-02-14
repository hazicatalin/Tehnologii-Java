package com.example.App;

import apptools.Email;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 */
@Path("/hello")
@Singleton
public class HelloController {

    @GET
    public String sayHello() {
        Email email = Email.getInstance();
        email.sendEmail("hazicatalin3@gmail.com", "test", "Hello, this is working");
        return email.gethello();
    }
}
