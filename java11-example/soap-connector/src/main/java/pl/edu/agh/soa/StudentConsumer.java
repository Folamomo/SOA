package pl.edu.agh.soa;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.io.*;
import java.util.Base64;
import java.util.List;

public class StudentConsumer {
    private final StudentController studentController;

    public StudentConsumer() {
        studentController = new StudentControllerService().getStudentControllerPort();
        ((BindingProvider)studentController).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "test");
        ((BindingProvider)studentController).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "abc-12345");
    }

    public void addTestStudents() {
        studentController.addStudent("Jan", "Kowalski", new Holder<>("1"));
        studentController.addStudent("Jan", "Nowak", new Holder<>("2"));
    }

    public void addTestCourses() throws StudentNotFoundException_Exception {
        studentController.addCourse("1", "Chemia");
        studentController.addCourse("2", "Biologia");
    }

    public List<Student> getTestStudents() {
        StudentArray all = studentController.getAllBy("Jan", "", "");
        return all.getItem();
    }

    public void addAvatar(String album, String imagePath) throws IOException, StudentNotFoundException_Exception {
        FileInputStream file = new FileInputStream(imagePath);
        String base64 = Base64.getEncoder().encodeToString(file.readAllBytes());
        studentController.addAvatar(album, base64);
    }

    public void saveAvatar(String album, String imagePath) throws StudentNotFoundException_Exception, IOException {
        byte[] base64 = Base64.getDecoder().decode(studentController.getStudentByAlbum(album).avatar);
        FileOutputStream out = new FileOutputStream(imagePath);
        out.write(base64);
    }
}
