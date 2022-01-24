/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Lab10.resilient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

@Path("/cb")
@ApplicationScoped
public class ResilienceCircuitBreaker {
    int x=0;
    @CircuitBreaker(
        successThreshold = 10,
        requestVolumeThreshold = 4,
        failureRatio=0.75,
        delay = 10000)
    @GET
    public String serviceA() {
       String conn = null;
       conn = connectionService();
       return conn;
}

    private String connectionService() {
        x++;
        if(x%5!=0)
            throw new UnsupportedOperationException("Not supported yet.");
        else
            return String.valueOf(x);
    }
}
