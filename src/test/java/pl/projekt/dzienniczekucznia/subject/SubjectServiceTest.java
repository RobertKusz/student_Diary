package pl.projekt.dzienniczekucznia.subject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.projekt.dzienniczekucznia.subject.dto.SubjectDto;
import pl.projekt.dzienniczekucznia.teacher.Teacher;
import pl.projekt.dzienniczekucznia.teacher.TeacherService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectServiceTest {
    @Mock SubjectRepository subjectRepository;
    @Mock TeacherService teacherService;

    @InjectMocks SubjectService subjectService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }
    //getSubjectById
    @Test
    void shouldReturnSubjectDtoById(){
        //given
        Subject subject = mock(Subject.class);
        when(subject.getId()).thenReturn(1L);
        when(subject.getName()).thenReturn("MatmaTest");
        when(subject.getDescription()).thenReturn("Test");
        Teacher teacher = mock(Teacher.class);
        when(subject.getTeacher()).thenReturn(teacher);
        when(subjectRepository.getSubjectById(1L)).thenReturn(subject);
        //when
        SubjectDto subjectById = subjectService.getSubjectById(1L);
        //then
        assertThat(subjectById.getId()).isEqualTo(subject.getId());
        assertThat(subjectById.getName()).isEqualTo(subject.getName());
        assertThat(subjectById.getDescription()).isEqualTo(subject.getDescription());
        assertThat(subjectById.getTeacher()).isEqualTo(subject.getTeacher());
    }
    //getAll
    @Test
    void shouldReturn3SubjectDto(){
        Subject subject1 = mock(Subject.class);
        Subject subject2 = mock(Subject.class);
        Subject subject3 = mock(Subject.class);
        List<Subject> subjects = List.of(subject1, subject2, subject3);
        when(subjectRepository.findAll()).thenReturn(subjects);
        //when
        List<SubjectDto> allSubjects = subjectService.getAll();
        //then
        assertThat(allSubjects.size()).isEqualTo(3);
    }
    @Test
    void shouldReturnListWithCorrectData(){
        Subject subject = mock(Subject.class);
        when(subject.getId()).thenReturn(1L);
        when(subject.getName()).thenReturn("MatmaTest");
        when(subject.getDescription()).thenReturn("Test");
        Teacher teacher = mock(Teacher.class);
        when(subject.getTeacher()).thenReturn(teacher);

        List<Subject> subjects = List.of(subject);
        when(subjectRepository.findAll()).thenReturn(subjects);
        //when
        List<SubjectDto> allSubjects = subjectService.getAll();
        SubjectDto subjectDto = allSubjects.get(0);
        //then
        assertThat(subjectDto.getId()).isEqualTo(subject.getId());
        assertThat(subjectDto.getName()).isEqualTo(subject.getName());
        assertThat(subjectDto.getDescription()).isEqualTo(subject.getDescription());
        assertThat(subjectDto.getTeacher()).isEqualTo(subject.getTeacher());
    }

    //getSubjectByName
    @Test
    void shouldReturnSubjectByName(){
        //given
        Subject subject = mock(Subject.class);
        when(subject.getId()).thenReturn(1L);
        when(subject.getName()).thenReturn("MatmaTest");
        when(subject.getDescription()).thenReturn("Test");
        Teacher teacher = mock(Teacher.class);
        when(subject.getTeacher()).thenReturn(teacher);

        when(subjectRepository.getSubjectByName("MatmaTest")).thenReturn(Optional.of(subject));
        //when
        SubjectDto subjectById = subjectService.getSubjectByName("MatmaTest");
        //then
        assertThat(subjectById.getId()).isEqualTo(subject.getId());
        assertThat(subjectById.getName()).isEqualTo(subject.getName());
        assertThat(subjectById.getDescription()).isEqualTo(subject.getDescription());
        assertThat(subjectById.getTeacher()).isEqualTo(subject.getTeacher());
    }
    @Test
    void shouldReturnNull(){
        when(subjectRepository.getSubjectByName("MatmaTest")).thenReturn(Optional.empty());
        //when
        SubjectDto subjectById = subjectService.getSubjectByName("MatmaTest");
        //then
        assertThat(subjectById).isEqualTo(null);
    }
    //addNewSubject
    @Test
    void shouldAddNewSubject(){
        //given
        Teacher teacher = mock(Teacher.class);
        when(teacherService.getTeacherById(1L)).thenReturn(teacher);
        //when
        subjectService.addNewSubject("MatmaTest",1L);
        //then
        ArgumentCaptor<Subject> captor = ArgumentCaptor.forClass(Subject.class);
        Mockito.verify(subjectRepository).save(captor.capture());

        Subject subject = captor.getValue();
        assertThat(subject.getTeacher()).isEqualTo(teacher);
        assertThat(subject.getName()).isEqualTo("MatmaTest");
    }
}