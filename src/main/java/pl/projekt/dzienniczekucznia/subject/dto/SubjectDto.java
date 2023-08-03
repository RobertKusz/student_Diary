package pl.projekt.dzienniczekucznia.subject.dto;

import pl.projekt.dzienniczekucznia.teacher.Teacher;

public class SubjectDto {
    private Long id;
    private String name;
    private String description;
    private Teacher teacher;

    public SubjectDto(Long id, String name, String description, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
