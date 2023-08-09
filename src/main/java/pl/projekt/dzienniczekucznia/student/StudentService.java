package pl.projekt.dzienniczekucznia.student;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projekt.dzienniczekucznia.student.dto.StudentDto;
import pl.projekt.dzienniczekucznia.student.dto.StudentRegistrationDto;
import pl.projekt.dzienniczekucznia.subject.Subject;
import pl.projekt.dzienniczekucznia.subject.SubjectDtoMapper;
import pl.projekt.dzienniczekucznia.subject.SubjectService;
import pl.projekt.dzienniczekucznia.subject.dto.SubjectDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubjectService subjectService;

    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder, SubjectService subjectService) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.subjectService = subjectService;
    }

    public long getUserNumber(){
        return studentRepository.findAll().size();
    }
    public List<StudentDto> getAllStudents(){
        return studentRepository.findAll()
                .stream()
                .map(StudentDtoMapper::map)
                .toList();
    }

    public Optional<StudentDto> getStudentById(Long id){
        return studentRepository.findById(id)
                .map(StudentDtoMapper::map);
    }
    public Student getStudentByLogin(String login){
     return studentRepository.findStudentByLogin(login);
    }
    @Transactional
    public void registerStudent(StudentRegistrationDto studentRegistration, Long[] subjectsId){
        Student student = new Student();
        student.setFirstName(studentRegistration.getFirstName());
        student.setLastName(studentRegistration.getLastName());
        student.setLogin(studentRegistration.getLogin());
        student.setPassword(passwordEncoder.encode(studentRegistration.getPassword()));
        student.setSubjects(saveSubjects(subjectsId));
        studentRepository.save(student);
    }

    private List<Subject> saveSubjects(Long[] subjectsId) {
        if (subjectsId == null)
            return null;
        List<SubjectDto> subjectsDto = changeSubjectIdToSubject(subjectsId);
        return subjectsDto.stream().map(SubjectDtoMapper::map).toList();
    }

    private List<SubjectDto> changeSubjectIdToSubject(Long[] subjectsId) {
        if (subjectsId == null){
            return null;
        }
        List<SubjectDto> subjectDto = new ArrayList<>();
        for (Long subjectId : subjectsId) {
            subjectDto.add(subjectService.getSubjectById(subjectId));
        }
        return subjectDto;

    }

    public String getThermometerValue(Double temperature) {
        String thermometerValue;
        if (temperature<= 0)
            thermometerValue = "empty";
        else if (temperature <= 10) {
            thermometerValue = "quarter";
        } else if (temperature <= 20) {
            thermometerValue ="half";
        } else if (temperature <= 30) {
            thermometerValue ="three-quarters";
        } else {
            thermometerValue = "full";
        }
        return thermometerValue;
    }
}
