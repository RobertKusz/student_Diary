package pl.projekt.dzienniczekucznia.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.projekt.dzienniczekucznia.behavioralNote.BehavioralNote;
import pl.projekt.dzienniczekucznia.behavioralNote.BehavioralNoteRepository;
import pl.projekt.dzienniczekucznia.behavioralNote.dto.BehavioralNoteDto;
import pl.projekt.dzienniczekucznia.student.dto.StudentDto;
import pl.projekt.dzienniczekucznia.student.dto.StudentRegistrationDto;
import pl.projekt.dzienniczekucznia.subject.Subject;
import pl.projekt.dzienniczekucznia.subject.SubjectDtoMapper;
import pl.projekt.dzienniczekucznia.subject.SubjectService;
import pl.projekt.dzienniczekucznia.subject.dto.SubjectDto;
import pl.projekt.dzienniczekucznia.teacher.Teacher;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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
    void shouldFindStudentWithValidData(){
        Student student = mock(Student.class);
        when(student.getId()).thenReturn(1L);
        when(student.getLogin()).thenReturn("login");
        when(student.getPassword()).thenReturn("password");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        //when
        Optional<StudentDto> studentById = studentService.getStudentById(1L);
        //then
        assertThat(studentById.get().getId()).isEqualTo(student.getId());
        assertThat(studentById.get().getId()).isEqualTo(student.getId());
        assertThat(studentById.get().getLogin()).isEqualTo(student.getLogin());
        assertThat(studentById.get().getPassword()).isEqualTo(student.getPassword());
    }
    //registerStudent
    @Test
    public void shouldRegisterStudent() {
        // given
        StudentRegistrationDto studentRegistrationDto = new StudentRegistrationDto();
        studentRegistrationDto.setFirstName("Bob");
        studentRegistrationDto.setLastName("Bobby");
        studentRegistrationDto.setPesel("12341234123");
        studentRegistrationDto.setLogin("login");
        studentRegistrationDto.setPassword("password");

        Long[] subjectsId = new Long[]{1L, 2L, 3L};

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");


        SubjectDto subjectDto1 = mock(SubjectDto.class);
        SubjectDto subjectDto2 = mock(SubjectDto.class);
        SubjectDto subjectDto3 = mock(SubjectDto.class);
        when(subjectService.getSubjectById(1L)).thenReturn(subjectDto1);
        when(subjectService.getSubjectById(2L)).thenReturn(subjectDto2);
        when(subjectService.getSubjectById(3L)).thenReturn(subjectDto3);

        // when
        studentService.registerStudent(studentRegistrationDto, subjectsId);

        // then
        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository, times(1)).save(captor.capture());

        Student savedStudent = captor.getValue();

        assertThat(savedStudent.getFirstName()).isEqualTo("Bob");
        assertThat(savedStudent.getLastName()).isEqualTo("Bobby");
        assertThat(savedStudent.getPesel()).isEqualTo("12341234123");
        assertThat(savedStudent.getLogin()).isEqualTo("login");
        assertThat(savedStudent.getPassword()).isEqualTo("encodedPassword");

    }

    //saveSubjects
    @Test
    void shouldSave3Subjects(){
        //given
        SubjectDto subject1 = mock(SubjectDto.class);
        SubjectDto subject2 = mock(SubjectDto.class);
        SubjectDto subject3 = mock(SubjectDto.class);
        when(subjectService.getSubjectById(1L)).thenReturn(subject1);
        when(subjectService.getSubjectById(2L)).thenReturn(subject2);
        when(subjectService.getSubjectById(3L)).thenReturn(subject3);
        Long[] subjectsId = new Long[]{1L,2L,3L};

        //when
        List<Subject> subjects = studentService.saveSubjects(subjectsId);
        //then
        assertThat(subjects.size()).isEqualTo(3);
    }
    @Test
    void shouldSave1SubjectWithValidData(){
        //given
        SubjectDto subject1 = mock(SubjectDto.class);
        when(subject1.getId()).thenReturn(1L);
        when(subject1.getName()).thenReturn("Test Matematyka");
        when(subject1.getDescription()).thenReturn("Test Opis");
        Teacher teacher = mock(Teacher.class);
        when(subject1.getTeacher()).thenReturn(teacher);
        Long[] subjectsId = new Long[]{1L};

        when(subjectService.getSubjectById(1L)).thenReturn(subject1);

        //when
        System.out.println();
        List<Subject> subjects = studentService.saveSubjects(subjectsId);
        Subject subject = subjects.get(0);
        //then

        assertThat(subject.getId()).isEqualTo(1L);
        assertThat(subject.getName()).isEqualTo("Test Matematyka");
        assertThat(subject.getDescription()).isEqualTo("Test Opis");
        assertThat(subject.getTeacher()).isEqualTo(teacher);

    }
    //getThermometerValue
    @ParameterizedTest
    @ValueSource(doubles = {-50,-10,0})
    void shouldReturnThermometerValue_Empty(Double thermometer){
        //when
        studentService.getThermometerValue(thermometer);
    }
    @ParameterizedTest
    @ValueSource(doubles = {1,5,10})
    void shouldReturnThermometerValue_quarter(Double thermometer){
        //when
        String thermometerValue = studentService.getThermometerValue(thermometer);
        //then
        assertThat(thermometerValue).isEqualTo("quarter");
    }
    @ParameterizedTest
    @ValueSource(doubles = {11,15,20})
    void shouldReturnThermometerValue_half(Double thermometer){
        //when
        String thermometerValue = studentService.getThermometerValue(thermometer);
        //then
        assertThat(thermometerValue).isEqualTo("half");
    }
    @ParameterizedTest
    @ValueSource(doubles = {21,25,30})
    void shouldReturnThermometerValue_three_quarters(Double thermometer){
        //when
        String thermometerValue = studentService.getThermometerValue(thermometer);
        //then
        assertThat(thermometerValue).isEqualTo("three-quarters");
    }
    @ParameterizedTest
    @ValueSource(doubles = {31,35,50})
    void shouldReturnThermometerValue_full(Double thermometer){
        //when
        String thermometerValue = studentService.getThermometerValue(thermometer);
        //then
        assertThat(thermometerValue).isEqualTo("full");
    }
    //getBehavioralNotes

    @Test
    void shouldFind3BehavioralNotes(){
        //given
        BehavioralNote note1 = mock(BehavioralNote.class);
        BehavioralNote note2 = mock(BehavioralNote.class);
        BehavioralNote note3 = mock(BehavioralNote.class);
        List<BehavioralNote> notes = List.of(note2, note1, note3);

        when(behavioralNoteRepository.findAll()).thenReturn(notes);
        //when
        List<BehavioralNoteDto> behavioralNotes = studentService.getBehavioralNotes();
        //then
        assertThat(behavioralNotes.size()).isEqualTo(3);
    }
    @Test
    void shouldFind1BehavioralNoteWithValidData(){
        //given
        BehavioralNote note1 = mock(BehavioralNote.class);
        when(note1.getId()).thenReturn(1L);
        when(note1.getDescription()).thenReturn("Test Description");
        Student student = mock(Student.class);
        when(note1.getStudent()).thenReturn(student);
        Teacher teacher = mock(Teacher.class);
        when(note1.getTeacher()).thenReturn(teacher);

        List<BehavioralNote> notes = List.of(note1);

        when(behavioralNoteRepository.findAll()).thenReturn(notes);
        //when
        List<BehavioralNoteDto> behavioralNotes = studentService.getBehavioralNotes();
        BehavioralNoteDto behavioralNoteDto = behavioralNotes.get(0);

        //then
        assertThat(behavioralNoteDto.getId()).isEqualTo(1L);
        assertThat(behavioralNoteDto.getDescription()).isEqualTo("Test Description");
        assertThat(behavioralNoteDto.getStudent()).isEqualTo(student);
        assertThat(behavioralNoteDto.getTeacher()).isEqualTo(teacher);
    }
    //getByLogin
    @Test
    void shouldFindStudentByLogin(){
        //given
        Student student = mock(Student.class);
        when(student.getId()).thenReturn(1L);
        when(student.getLogin()).thenReturn("login");
        when(student.getPassword()).thenReturn("password");
        when(student.getFirstName()).thenReturn("Bob");
        when(student.getLastName()).thenReturn("Kowalski");
        when(student.getPesel()).thenReturn("12345123451");

        when(studentRepository.getStudentByLogin("login")).thenReturn(Optional.of(student));
        //when
        Optional<StudentDto> studentDtoOptional = studentService.getByLogin("login");
        StudentDto studentDto = studentDtoOptional.get();
        //then
        assertThat(studentDto.getId()).isEqualTo(1L);
        assertThat(studentDto.getLogin()).isEqualTo("login");
        assertThat(studentDto.getPassword()).isEqualTo("password");
        assertThat(studentDto.getFirstName()).isEqualTo("Bob");
        assertThat(studentDto.getLastName()).isEqualTo("Kowalski");
        assertThat(studentDto.getPesel()).isEqualTo("12345123451");

    }




}