/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Lab10.resilient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

/**
 *
 * @author hazi_
 */
@Path("/retry")
@ApplicationScoped
public class ResilienceRetry{
    int x=0;
    
    @Retry(maxRetries = 2, delay = 200, jitter = 50)
    @Fallback(fallbackMethod = "fallback")
    @GET
    public String doWork() {
        return callAnotherService(); 
    // This service usually works...
    }
    private String fallback() {
        return "fallback";
    }

    private String callAnotherService() {
        x++;
        if(x<15)
            throw new UnsupportedOperationException("Not supported yet.");
        else
            return String.valueOf(x);
    }

}

