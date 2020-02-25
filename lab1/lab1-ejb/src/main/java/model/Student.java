package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 2020-02-25.
 */
public class Student {
    private String firstName;
    private String lastName;
    private String album;
    private List<String> courses;

    public Student(String firstName, String lastName, String album) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.album = album;
        this.courses = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastNamel) {
        this.lastName = lastNamel;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @XmlElementWrapper(name = "courses")
    @XmlElement(name = "course")
    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public void addCourse(String course){

    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastNamel='" + lastName + '\'' +
                ", album='" + album + '\'' +
                ", courses=" + courses +
                '}';
    }

}


