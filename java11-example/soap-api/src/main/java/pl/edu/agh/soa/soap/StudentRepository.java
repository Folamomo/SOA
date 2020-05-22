package pl.edu.agh.soa.soap;

import org.jboss.logging.Logger;
import pl.edu.agh.soa.soap.model.Student;

import java.util.*;

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
        return student;
    }

    public Optional<Student> getByAlbum(String album){
        return Optional.ofNullable(data.get(album));
    }

    public Collection<Student> getAll(){
        return data.values();
    }
}
