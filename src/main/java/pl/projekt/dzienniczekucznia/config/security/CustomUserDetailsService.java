package pl.projekt.dzienniczekucznia.config.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.projekt.dzienniczekucznia.student.Student;
import pl.projekt.dzienniczekucznia.student.StudentService;
import pl.projekt.dzienniczekucznia.teacher.Teacher;
import pl.projekt.dzienniczekucznia.teacher.TeacherService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentService studentService;
    private final TeacherService teacherService;

    public CustomUserDetailsService(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Student student = studentService.getStudentByLogin(login);
        Teacher teacher = teacherService.getByLogin(login);

        if (student != null){
            return User.withUsername(student.getLogin())
                    .password(student.getPassword())
                    .roles("STUDENT")
                    .build();
        } else if (teacher != null) {
            return User.withUsername(teacher.getLogin())
                    .password(teacher.getPassword())
                    .roles("TEACHER")
                    .build();
        }else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}