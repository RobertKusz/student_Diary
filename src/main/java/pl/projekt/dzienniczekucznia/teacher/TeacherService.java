package pl.projekt.dzienniczekucznia.teacher;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projekt.dzienniczekucznia.teacher.dto.TeacherRegistrationDto;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    public TeacherService(TeacherRepository teacherRepository, PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Teacher getByLogin(String login){
        return teacherRepository.getTeacherByLogin(login);
    }

    @Transactional
    public void registerTeacher(TeacherRegistrationDto teacherRegistration){
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherRegistration.getFirstName());
        teacher.setLastName(teacherRegistration.getLastName());
        teacher.setLogin(teacherRegistration.getLogin());
        teacher.setPassword(passwordEncoder.encode(teacherRegistration.getPassword()));
        teacher.setSubject(teacherRegistration.getSubject());
        teacherRepository.save(teacher);
    }

    public long getNewTeacherId(){
        Optional<Teacher> lastTeacher = teacherRepository.findTopByOrderByIdDesc();
        return lastTeacher.map(Teacher::getId).orElseThrow();
    }

    public Teacher getTeacherById(Long id){
        return teacherRepository.getTeacherById(id).orElseThrow(()->new NoSuchElementException("Brak nauczyciela o danym id"));
    }
}
