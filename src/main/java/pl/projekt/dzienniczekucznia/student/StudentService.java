package pl.projekt.dzienniczekucznia.student;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projekt.dzienniczekucznia.behavioralNote.BehavioralNoteDtoMapper;
import pl.projekt.dzienniczekucznia.behavioralNote.BehavioralNoteRepository;
import pl.projekt.dzienniczekucznia.behavioralNote.dto.BehavioralNoteDto;
import pl.projekt.dzienniczekucznia.student.dto.StudentDto;
import pl.projekt.dzienniczekucznia.student.dto.StudentRegistrationDto;
import pl.projekt.dzienniczekucznia.subject.Subject;
import pl.projekt.dzienniczekucznia.subject.SubjectDtoMapper;
import pl.projekt.dzienniczekucznia.subject.SubjectService;
import pl.projekt.dzienniczekucznia.subject.dto.SubjectDto;
import pl.projekt.dzienniczekucznia.teacher.dto.TeacherDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubjectService subjectService;
    private final BehavioralNoteRepository behavioralNoteRepository;

    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder, SubjectService subjectService, BehavioralNoteRepository behavioralNoteRepository) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.subjectService = subjectService;
        this.behavioralNoteRepository = behavioralNoteRepository;
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
    public Optional<Student> getStudentByLogin(String login){
     return studentRepository.findStudentByLogin(login);
    }
    @Transactional
    public void registerStudent(StudentRegistrationDto studentRegistration, Long[] subjectsId){
        Student student = new Student();
        student.setFirstName(studentRegistration.getFirstName());
        student.setLastName(studentRegistration.getLastName());
        student.setLogin(studentRegistration.getLogin());
        student.setPesel(studentRegistration.getPesel());
        student.setPassword(passwordEncoder.encode(studentRegistration.getPassword()));
        student.setSubjects(saveSubjects(subjectsId));
        studentRepository.save(student);
    }

    List<Subject> saveSubjects(Long[] subjectsId) {
        if (subjectsId == null)
            return null;
        List<SubjectDto> subjectsDto = changeSubjectIdToSubject(subjectsId);
        return subjectsDto
                .stream()
                .map(SubjectDtoMapper::map)
                .toList();
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
    public List<BehavioralNoteDto> getBehavioralNotes(){

        return behavioralNoteRepository.findAll()
                .stream()
                .map(BehavioralNoteDtoMapper::map)
                .toList();
    }

    public Optional<StudentDto> getByLogin(String login) {
        return studentRepository
                .getStudentByLogin(login)
                .map(StudentDtoMapper::map);
    }
}
