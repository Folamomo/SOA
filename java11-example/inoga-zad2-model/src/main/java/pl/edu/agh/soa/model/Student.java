package pl.edu.agh.soa.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor

public class Student implements Serializable {
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String album;
    @JsonIgnore
    private String avatar;
    private List<String> courses;

    @JsonProperty(value = "courses")
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
