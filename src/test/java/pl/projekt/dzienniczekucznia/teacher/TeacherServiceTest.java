package pl.projekt.dzienniczekucznia.teacher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.projekt.dzienniczekucznia.subject.Subject;
import pl.projekt.dzienniczekucznia.teacher.dto.TeacherDto;
import pl.projekt.dzienniczekucznia.teacher.dto.TeacherRegistrationDto;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    @Mock TeacherRepository teacherRepository;
    @Mock PasswordEncoder passwordEncoder;

    @InjectMocks TeacherService teacherService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    //getByLogin
    @Test
    void shouldFindTeacherDtoByLogin(){
        //given
        Teacher teacher = mock(Teacher.class);
        when(teacher.getId()).thenReturn(1L);
        when(teacher.getLogin()).thenReturn("loginTest");
        when(teacher.getPassword()).thenReturn("passwordTest");
        when(teacher.getFirstName()).thenReturn("Bob");
        when(teacher.getLastName()).thenReturn("Baggins");
        Subject subject = mock(Subject.class);
        when(teacher.getSubject()).thenReturn(subject);
        Mockito.when(teacherRepository.getTeacherByLogin("loginTest")).thenReturn(Optional.of(teacher));

        //when
        Optional<TeacherDto> teacherDtoOptional = teacherService.getByLogin("loginTest");
        TeacherDto teacherDto = teacherDtoOptional.get();

        //then
        assertThat(teacherDto.getId()).isEqualTo(teacher.getId());
        assertThat(teacherDto.getLogin()).isEqualTo(teacher.getLogin());
        assertThat(teacherDto.getPassword()).isEqualTo(teacher.getPassword());
        assertThat(teacherDto.getFirstName()).isEqualTo(teacher.getFirstName());
        assertThat(teacherDto.getLastName()).isEqualTo(teacher.getLastName());
        assertThat(teacherDto.getSubject()).isEqualTo(teacher.getSubject());
    }
    @Test
    public void testGetByLoginNotFound() {
        //given
        when(teacherRepository.getTeacherByLogin("login")).thenReturn(Optional.empty());

        //when
        Optional<TeacherDto> teacherDtoOptional = teacherService.getByLogin("login");

        //then
        assertThat(teacherDtoOptional).isEmpty();
    }

    //registerTeacher
    @Test
    void shouldRegisterTeacher(){
        //given
        TeacherRegistrationDto registration = mock(TeacherRegistrationDto.class);
        when(registration.getFirstName()).thenReturn("NameTest");
        when(registration.getLastName()).thenReturn("LastNAmeTest");
        when(registration.getLogin()).thenReturn("LoginTest");

        Subject subject = mock(Subject.class);
        when(registration.getSubject()).thenReturn(subject);

        when(registration.getPassword()).thenReturn("passwordTest");
        when(passwordEncoder.encode("passwordTest")).thenReturn("EncodedPasswordTest");

        //when
        teacherService.registerTeacher(registration);

        //then
        ArgumentCaptor<Teacher> captor = ArgumentCaptor.forClass(Teacher.class);
        verify(teacherRepository).save(captor.capture());
        Teacher teacher = captor.getValue();

        assertThat(teacher.getLogin()).isEqualTo(registration.getLogin());
        assertThat(teacher.getPassword()).isEqualTo("EncodedPasswordTest");
        assertThat(teacher.getFirstName()).isEqualTo(registration.getFirstName());
        assertThat(teacher.getLastName()).isEqualTo(registration.getLastName());
        assertThat(teacher.getSubject()).isEqualTo(registration.getSubject());
    }
    //getNewTeacherId
    @Test
    void shouldFindTeacherWithHighestId(){
        Teacher teacher = mock(Teacher.class);
        when(teacher.getId()).thenReturn(1L);
        when(teacherRepository.findTopByOrderByIdDesc()).thenReturn(Optional.of(teacher));
        //when
        long newTeacherId = teacherService.getNewTeacherId();

        //then
        assertThat(newTeacherId).isEqualTo(teacher.getId());
    }
    @Test
    void shouldNotFindTeacher(){
        Teacher teacher = mock(Teacher.class);
        when(teacher.getId()).thenReturn(1L);
        when(teacherRepository.findTopByOrderByIdDesc()).thenReturn(Optional.empty());
        //when

        //then
        assertThatThrownBy(()->teacherService.getNewTeacherId())
                .isInstanceOf(NoSuchElementException.class);
    }
    //getTeacherById
    @Test
    void shouldFindTeacherById(){
        Teacher teacher = mock(Teacher.class);
        when(teacher.getId()).thenReturn(1L);
        when(teacher.getLogin()).thenReturn("loginTest");
        when(teacher.getPassword()).thenReturn("passwordTest");
        when(teacher.getFirstName()).thenReturn("Bob");
        when(teacher.getLastName()).thenReturn("Baggins");
        Subject subject = mock(Subject.class);
        when(teacher.getSubject()).thenReturn(subject);
        Mockito.when(teacherRepository.getTeacherByLogin("loginTest")).thenReturn(Optional.of(teacher));

        when(teacherRepository.getTeacherById(1L)).thenReturn(Optional.of(teacher));
        //when
        Teacher teacherById = teacherService.getTeacherById(1L);
        //then
        assertThat(teacherById.getId()).isEqualTo(teacher.getId());
        assertThat(teacherById.getLogin()).isEqualTo(teacher.getLogin());
        assertThat(teacherById.getPassword()).isEqualTo(teacher.getPassword());
        assertThat(teacherById.getFirstName()).isEqualTo(teacher.getFirstName());
        assertThat(teacherById.getLastName()).isEqualTo(teacher.getLastName());
        assertThat(teacherById.getSubject()).isEqualTo(teacher.getSubject());
    }
}