package pl.edu.agh.soa.soap;

import org.jboss.logging.Logger;
import pl.edu.agh.soa.model.Student;

import javax.jws.WebParam;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentRepository {
    private Logger LOGGER;
    private Map<String, Student> data;

    public StudentRepository() {
        LOGGER = Logger.getLogger(this.getClass());
        this.data = new HashMap<>();

        Student mockStudent = Student.builder()
                .album("1")
                .avatar("")
                .firstName("Jan")
                .lastName("Kowalski")
                .courses(List.of("Kurs1", "Kurs2", "Kurs3"))
                .build();
        save(mockStudent);
    }

    public Student save(Student student){
        data.put(student.getAlbum(), student);
        LOGGER.info("Saved Student " + student.getLastName());
        return student;
    }

    public Optional<Student> getByAlbum(String album){
        return Optional.ofNullable(data.get(album));
    }

    public Collection<Student> getAll(){
        return data.values();
    }
    public Collection<Student> findAllBy(String firstName, String lastName, String album){
        Predicate<Student> matches = student ->
                (firstName==null || firstName.equals("") || firstName.equals(student.getFirstName())) &&
                        (lastName==null || lastName.equals("") || lastName.equals(student.getLastName())) &&
                        (album == null || album.equals("") || album.equals(student.getAlbum()));

        return  data.values().stream()
                .filter(matches)
                .collect(Collectors.toList());
    }
}
