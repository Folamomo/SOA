package pl.edu.agh.soa;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws StudentNotFoundException_Exception, IOException {
        StudentConsumer consumer = new StudentConsumer();
        consumer.addTestStudents();
        consumer.addTestCourses();
        consumer.getTestStudents().forEach(student -> System.out.println(student.lastName));
        consumer.addAvatar("1", "student.png");
        consumer.saveAvatar("1", "saved.png");
    }
}
