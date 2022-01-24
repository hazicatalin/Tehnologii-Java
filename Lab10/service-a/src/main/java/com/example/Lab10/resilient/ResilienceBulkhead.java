/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Lab10.resilient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import okhttp3.Connection;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;

@Path("/bulkhead")
@ApplicationScoped
public class ResilienceBulkhead {
    int i=0;
    
    @Path("/semaphore")
    @GET
    @Bulkhead(5)
    public String bulkhead() {
        i++;
        try {
            Thread.sleep(10);//artificially generates contention
            return String.valueOf(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            i--;
        }
    }
    
    @Path("/threadpool")
    @GET
    @Asynchronous
    @Bulkhead(value = 5, waitingTaskQueue = 8)
    public Future<String> serviceA() {
        String conn = null;
        conn = connectionService();
        return CompletableFuture.completedFuture(conn);
    }

    private String connectionService() {
        return "success"; //To change body of generated methods, choose Tools | Templates.
    }
}
