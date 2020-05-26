package pl.edu.agh.soa.jpa;

import lombok.Data;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class StudentEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long studentId;
    private String album;
    private String firstName;
    private String lastName;
    @OneToMany(cascade = CascadeType.ALL)
    private List<BookEntity> books;
    @ManyToMany
    @JoinTable
    private List<CourseEntity> courses;
    @OneToOne(cascade = CascadeType.ALL)
    private AvatarEntity avatar;
}
