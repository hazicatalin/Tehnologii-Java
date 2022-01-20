package com.example.Lab9;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 */
@Path("/hello")
@Singleton
public class HelloController {
    
    private final OkHttpClient httpClient = new OkHttpClient();
    
    @GET
    @Path("/docs")
    @Produces(MediaType.TEXT_PLAIN)
    public String geDocs() throws Exception {
        String rsp;
        Request request = new Request.Builder()
                .url("http://localhost:9080/data/app/docs")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            rsp = response.body().string();
        }
        return rsp;
    }
    
    @GET
    @Path("/docs/{user}")
    @Produces(MediaType.TEXT_PLAIN)
    public String geDocs(@PathParam("user") String user) throws Exception {
        String rsp;
        String url = "http://localhost:9080/data/app/docs/"+user;
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            rsp = response.body().string();
        }
        return rsp;
    }
    
    
    @POST
    @Path("/upload")
    public String addFile(String base64Encode,
            @QueryParam("filename") String filename,
            @QueryParam("username") String username)  throws Exception {
        HttpUrl url = HttpUrl.parse("http://localhost:9080/data/app/upload").newBuilder()
        .addEncodedPathSegment(base64Encode)
        .addQueryParameter("filename", filename)
        .addQueryParameter("username", username)
        .build();

        Request request = new Request.Builder()
               .url(url)
               .build();
        return "Success";
    } 
    
    @PUT
    @Path("/update/{filename}")
    public String updateDoc(@PathParam("filename") String filename,
        String base64Encode) {
        HttpUrl url = HttpUrl.parse("http://localhost:9080/data/app/upload/"+filename).newBuilder()
        .addEncodedPathSegment(base64Encode)
        .build();

        Request request = new Request.Builder()
               .url(url)
               .build();
        return "Success";
    }
    
    @DELETE
    @Path("delete/{filename}")
    public String deleteProduct(@PathParam("filename") String filename){
        String url = "http://localhost:9080/data/app/delete/"+filename;
        String rsp = null;
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            rsp = response.body().string();
        } catch (IOException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rsp;
    }
}
