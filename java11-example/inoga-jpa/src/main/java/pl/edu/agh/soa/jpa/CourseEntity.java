package pl.edu.agh.soa.jpa;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class CourseEntity {
    @Id @GeneratedValue
    private Long courseId;
    String name;
    @ManyToMany
    @JoinTable
    private List<StudentEntity> students;
}
