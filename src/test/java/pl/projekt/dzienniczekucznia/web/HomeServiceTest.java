package pl.projekt.dzienniczekucznia.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.projekt.dzienniczekucznia.student.Student;
import pl.projekt.dzienniczekucznia.student.StudentRepository;
import pl.projekt.dzienniczekucznia.teacher.Teacher;
import pl.projekt.dzienniczekucznia.teacher.TeacherRepository;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HomeServiceTest {

    @Mock StudentRepository studentRepository;
    @Mock TeacherRepository teacherRepository;
    @InjectMocks HomeService homeService;

    @BeforeEach
    void inti(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnSTUDENT(){
        //given
        Student student = mock(Student.class);
        when(studentRepository.getStudentByLogin("student")).thenReturn(Optional.of(student));
        //when
        String result = homeService.getRole("student");
        //then
        Assertions.assertThat(result).isEqualTo("STUDENT");
    }
    @Test
    void shouldReturnTEACHER(){
        //given
        Teacher teacher = mock(Teacher.class);
        when(teacherRepository.getTeacherByLogin("teacher")).thenReturn(Optional.of(teacher));
        //when
        String result = homeService.getRole("teacher");
        //then
        Assertions.assertThat(result).isEqualTo("TEACHER");
    }
    @Test
    void shouldReturnNone(){
        //when
        String result = homeService.getRole("teacher");
        //then
        Assertions.assertThat(result).isEqualTo("none");
    }
}