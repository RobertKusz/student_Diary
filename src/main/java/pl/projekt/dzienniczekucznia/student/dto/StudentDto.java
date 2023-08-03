package pl.projekt.dzienniczekucznia.student.dto;

import jakarta.persistence.*;
import pl.projekt.dzienniczekucznia.grades.Grade;
import pl.projekt.dzienniczekucznia.subject.Subject;

import java.util.List;

public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String pesel;

    private List<Subject> subjects;

    private String login;
    private String password;

    public StudentDto(Long id, String firstName, String lastName, String pesel, List<Subject> subjects, String login, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.subjects = subjects;
        this.login = login;
        this.password = password;
    }

    public StudentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
