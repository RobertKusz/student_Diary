package pl.projekt.dzienniczekucznia.config.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.projekt.dzienniczekucznia.student.Student;
import pl.projekt.dzienniczekucznia.student.StudentService;
import pl.projekt.dzienniczekucznia.teacher.TeacherService;
import pl.projekt.dzienniczekucznia.teacher.dto.TeacherDto;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentService studentService;
    private final TeacherService teacherService;

    public CustomUserDetailsService(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        Optional<Student> student = studentService.getStudentByLogin(login);
        Optional<TeacherDto> teacher = teacherService.getByLogin(login);

        if (student.isPresent()){
            System.out.println("student");
            return User.withUsername(student.get().getLogin())
                    .password(student.get().getPassword())
                    .roles("STUDENT")
                    .build();

        } else if (teacher.isPresent()) {
            System.out.println("nauczyciel");
            return User.withUsername(teacher.get().getLogin())
                    .password(teacher.get().getPassword())
                    .roles("TEACHER")
                    .build();
        }else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}