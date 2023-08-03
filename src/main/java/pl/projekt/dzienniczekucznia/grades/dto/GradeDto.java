package pl.projekt.dzienniczekucznia.grades.dto;

import pl.projekt.dzienniczekucznia.student.Student;
import pl.projekt.dzienniczekucznia.subject.Subject;

import java.util.List;

public class GradeDto {
    private Long id;
    private int gradeValue;
    private Student student;
    private Subject subject;

    public GradeDto(Long id, int gradeValue, Student student, Subject subject) {
        this.id = id;
        this.gradeValue = gradeValue;
        this.student = student;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(int gradeValue) {
        this.gradeValue = gradeValue;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
