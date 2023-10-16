package pl.projekt.dzienniczekucznia.student;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.projekt.dzienniczekucznia.behavioralNote.BehavioralNoteRepository;
import pl.projekt.dzienniczekucznia.student.dto.StudentDto;
import pl.projekt.dzienniczekucznia.subject.SubjectService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    @Mock StudentRepository studentRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock SubjectService subjectService;
    @Mock BehavioralNoteRepository behavioralNoteRepository;
    @InjectMocks StudentService studentService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }
    //getUserNumber
    @Test
    void shouldReturnNumberOfStudents(){
        //given
        Student student1 = mock(Student.class);
        Student student3 = mock(Student.class);
        Student student2 = mock(Student.class);
        List<Student> students = List.of(student1, student2, student3);
        when(studentRepository.findAll()).thenReturn(students);
        //when
        long userNumber = studentService.getUserNumber();
        //then
        assertThat(userNumber).isEqualTo(students.size());
    }
    @Test
    void shouldReturn0Users(){
        //given
        List<Student> students = List.of();
        when(studentRepository.findAll()).thenReturn(students);
        //when
        long userNumber = studentService.getUserNumber();
        //then
        assertThat(userNumber).isEqualTo(0L);
    }
    //getAllStudents
    @Test
    void shouldCheckListLength(){
        //given
        Student student1 = mock(Student.class);
        Student student3 = mock(Student.class);
        Student student2 = mock(Student.class);
        List<Student> students = List.of(student1, student2, student3);
        when(studentRepository.findAll()).thenReturn(students);
        //when
        List<StudentDto> userNumber = studentService.getAllStudents();
        //then
        assertThat(userNumber.size()).isEqualTo(students.size());
    }
    @Test
    void shouldCheckBasicDataOfFirstOnList(){
        //given
        Student student1 = mock(Student.class);
        when(student1.getId()).thenReturn(1L);
        when(student1.getLogin()).thenReturn("login");
        when(student1.getPassword()).thenReturn("password");
        when(student1.getFirstName()).thenReturn("Bob");
        when(student1.getLastName()).thenReturn("Kowalski");

        Student student3 = mock(Student.class);
        Student student2 = mock(Student.class);
        List<Student> students = List.of(student1, student2, student3);
        when(studentRepository.findAll()).thenReturn(students);
        //when
        List<StudentDto> userNumber = studentService.getAllStudents();
        //then
        assertThat(userNumber.get(0).getId()).isEqualTo(students.get(0).getId());
        assertThat(userNumber.get(0).getLogin()).isEqualTo(students.get(0).getLogin());
        assertThat(userNumber.get(0).getLastName()).isEqualTo(students.get(0).getLastName());
        assertThat(userNumber.get(0).getPassword()).isEqualTo(students.get(0).getPassword());
        assertThat(userNumber.get(0).getFirstName()).isEqualTo(students.get(0).getFirstName());
    }
    //getStudentById
    @Test
    void should(){
        Student student = mock(Student.class);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<StudentDto> studentById = studentService.getStudentById(1L);
    }




}