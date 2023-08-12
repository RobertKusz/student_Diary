package pl.projekt.dzienniczekucznia.behavioralNote.dto;
import pl.projekt.dzienniczekucznia.student.Student;
import pl.projekt.dzienniczekucznia.teacher.Teacher;

public class BehavioralNoteDto {
    private Long id;
    private Teacher teacher;
    private Student student;
    private String description;

    public BehavioralNoteDto(Long id, Teacher teacher, Student student, String description) {
        this.id = id;
        this.teacher = teacher;
        this.student = student;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
