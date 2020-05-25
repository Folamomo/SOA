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

    public void getAvatar(String album, File imagePath){
        Response response = client.target(servicePath + "/{album}/avatar").request(MediaType.TEXT_PLAIN).get();
        byte[] base64 = Base64.getDecoder().decode(response.readEntity(String.class));
        response.close();
        try(FileOutputStream out = new FileOutputStream(imagePath)) {
            out.write(base64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() throws Exception {
        client.close();
    }
}
