package pl.edu.agh.soa.soap.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class Student {
    private String firstName;
    private String lastName;
    private String album;
    private String avatar;
    private List<String> courses;

    @XmlElementWrapper(name = "courses")
    @XmlElement(name = "course")
    public List<String> getCourses() {
        return courses;
    }

    public void addCourse(String course){
        if (this.courses == null) this.courses = new ArrayList<>();
        courses.add(course);
    }

    @JsonCreator
    public Student(@JsonProperty(value = "firstName", required = true)String firstName,
    @JsonProperty(value = "lastName", required = true)String lastName,
    @JsonProperty(value = "album", required = true)String album,
    @JsonProperty(value = "avatarBase64")String avatarBase64,
    @JsonProperty(value = "courses")List<String> courses){
        this.firstName = firstName;
        this.lastName = lastName;
        this.album = album;
        this.avatar = avatarBase64;
        this.courses = courses;
    }
}
