package pl.projekt.dzienniczekucznia.web;

import org.springframework.stereotype.Component;
import pl.projekt.dzienniczekucznia.student.StudentRepository;
import pl.projekt.dzienniczekucznia.teacher.TeacherRepository;

@Component
public class HomeService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public HomeService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public String getRole(String name) {
        if (studentRepository.getStudentByLogin(name).isPresent()){
            return "STUDENT";
        } else if (teacherRepository.getTeacherByLogin(name).isPresent()) {
            return "TEACHER";
        }else{
            return "none";
        }
    }
}
