package pl.edu.agh.soa.rest;

import pl.edu.agh.soa.model.Student;

import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        try(StudentRestConsumer consumer = new StudentRestConsumer()){
            Collection<Student> students = consumer.getAll();
            students.forEach(System.out::println);
            consumer.setAvatar("1");
            consumer.getAvatar("1", "rest.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
