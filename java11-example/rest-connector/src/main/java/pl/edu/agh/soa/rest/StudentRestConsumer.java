package pl.edu.agh.soa.rest;

import org.eclipse.microprofile.jwt.JsonWebToken;
import pl.edu.agh.soa.model.Student;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Base64;
import java.util.Collection;

public class StudentRestConsumer implements AutoCloseable{
    private Client client;
    private static final String servicePath = "http://localhost:8080/rest-api/api/Student/";
    private static final String authPath = "http://localhost:8180/auth/realms/myrealm/protocol/openid-connect/token";
    private String auth = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ1aW81bFppVU9tN19RZ1JISTRYOWpwRXFlVkN3THBfekZMOWxUMWJ5TkR3In0.eyJqdGkiOiI2ZTkzNTExMS1mYzY2LTQzMmQtYmFlMC0zNTdhYWU3M2JmZTMiLCJleHAiOjE1OTA0MzczMzQsIm5iZiI6MCwiaWF0IjoxNTkwNDM3MDM0LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODAvYXV0aC9yZWFsbXMvbXlyZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJhMTliMmFmYy1lOTZlLTQ5MzktODJiZi1hYTRiNTg5ZGUxMzYiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJqd3QtY2xpZW50IiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiNjhlZjVjMzAtY2IxNS00ZGVhLThkMWEtNmY4ZDcwZTQ4YTc0IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IlRoZW8gVGVzdGVyIiwiZ3JvdXBzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl0sInByZWZlcnJlZF91c2VybmFtZSI6InRlc3QiLCJnaXZlbl9uYW1lIjoiVGhlbyIsImZhbWlseV9uYW1lIjoiVGVzdGVyIiwiZW1haWwiOiJ0ZXN0ZXJAbG9jYWxob3N0In0.XEmuMssQ1jpiYd77kIwWI6MXu5vIAcAxY_px2WOY3U1Ra6j7rqaXZ6rBAf9gK1NDR59bkyn2p7vPi22H44e4pC6UXA80uBjHrbS90yKSFN9uXwqmRnRimLuOMjIryibxAJlRLqIhz_G1RcBH8f5XC3zkxPgKtZEmIyS1FNbE_o18yovj2IA55iQ_8Yeuq1xj8b0mMd0AbHBhkNvYGwhvHyaiLxCBfkGc__0NIDelyHQBsxNNk6IkXVhGvgSCCOQvUp5ULtNJzYciVTLlNgRhmwVTMrVGOV0rXPgCNWbIwuoe_uYMPg-f6LcU65N_MXrproRNNwFCxTmqNdeUFYQtQQ";

    public StudentRestConsumer() {
        this.client = ClientBuilder.newClient();

    }

    public Collection<Student> getAll(){
        Response response = client.target(servicePath + "all").request(MediaType.APPLICATION_JSON).get();
        Collection<Student> students = response.readEntity(new GenericType<Collection<Student>>() {
        });
        response.close();
        return students;
    }

    public void getAvatar(String album, String imagePath){
        Response response = client.target(servicePath + "/{album}/avatar")
                .resolveTemplate("album", album)
                .request(MediaType.TEXT_PLAIN).get();
        byte[] base64 = Base64.getDecoder().decode(response.readEntity(String.class));
        response.close();
        try(FileOutputStream out = new FileOutputStream(imagePath)) {
            out.write(base64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAvatar(String album){
        byte[] base64 = Base64.getEncoder().encode("a".getBytes());
        Response response = client.target(servicePath + "/{album}/setAvatar" )
                .resolveTemplate("album", album)
                .queryParam("base64Image", base64)
                .request(MediaType.TEXT_PLAIN)
                .header("Authorization", "Bearer " + auth)
                .post(null);
        System.out.println(response.getStatus());
        response.close();
    }

    @Override
    public void close() throws Exception {
        client.close();
    }
}
