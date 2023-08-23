package pl.projekt.dzienniczekucznia.exam.dto;

import jakarta.persistence.*;
import pl.projekt.dzienniczekucznia.subject.Subject;
import pl.projekt.dzienniczekucznia.teacher.Teacher;

import java.util.Date;

public class ExamDto {
    private Long id;
    private Date date;
    private String description;
    private Teacher teacher;
    private Subject subject;

    public ExamDto(Long id, Date date, String description, Teacher teacher, Subject subject) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.teacher = teacher;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
