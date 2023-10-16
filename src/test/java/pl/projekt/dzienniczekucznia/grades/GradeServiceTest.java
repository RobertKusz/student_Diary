package pl.projekt.dzienniczekucznia.grades;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.projekt.dzienniczekucznia.grades.dto.GradeDto;
import pl.projekt.dzienniczekucznia.student.Student;
import pl.projekt.dzienniczekucznia.student.StudentRepository;
import pl.projekt.dzienniczekucznia.subject.Subject;
import pl.projekt.dzienniczekucznia.subject.SubjectRepository;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GradeServiceTest {
    @Mock GradeRepository gradeRepository;
    @Mock StudentRepository studentRepository;
    @Mock SubjectRepository subjectRepository;

    @InjectMocks GradeService gradeService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    //getAvgGrades
    @Test
    void shouldReturnCorrectAverageOfAllGrades(){
        //given
        Student student1 = mock(Student.class);
        when(student1.getId()).thenReturn(1L);
        Grade grade1 = mock(Grade.class);
        when(grade1.getGradeValue()).thenReturn(4);
        Grade grade2 = mock(Grade.class);
        when(grade2.getGradeValue()).thenReturn(3);
        List<Grade> grades1 = List.of(grade1, grade2);

        Student student2 = mock(Student.class);
        when(student2.getId()).thenReturn(2L);
        Grade grade3 = mock(Grade.class);
        when(grade3.getGradeValue()).thenReturn(1);
        Grade grade4 = mock(Grade.class);
        when(grade4.getGradeValue()).thenReturn(2);
        List<Grade> grades2 = List.of(grade3, grade4);

        List<Student> students = List.of(student1, student2);

        when(studentRepository.findAll()).thenReturn(students);
        when(gradeRepository.getGradeByStudent(student1)).thenReturn(grades1);
        when(gradeRepository.getGradeByStudent(student2)).thenReturn(grades2);

        //when
        Map<Long, Double> avgGrades = gradeService.getAvgGrades();
        //then
        assertThat(avgGrades.get(1L)).isEqualTo(3.50);
        assertThat(avgGrades.get(2L)).isEqualTo(1.50);
    }

    @Test
    void shouldReturn0(){
        List<Student> students = List.of();
        when(studentRepository.findAll()).thenReturn(students);
        //when
        Map<Long, Double> avgGrades = gradeService.getAvgGrades();
        //then
        assertThat(avgGrades).isEmpty();
    }


    @Test
    void shouldReturnCorrectAverageOfAllStudentsGradesWithOneStudentWith0Grade(){
        //given
        Student student1 = mock(Student.class);
        when(student1.getId()).thenReturn(1L);
        Grade grade1 = mock(Grade.class);
        when(grade1.getGradeValue()).thenReturn(4);
        Grade grade2 = mock(Grade.class);
        when(grade2.getGradeValue()).thenReturn(3);
        List<Grade> grades1 = List.of(grade1, grade2);

        Student student2 = mock(Student.class);
        when(student2.getId()).thenReturn(2L);
        List<Grade> grades2 = List.of();

        List<Student> students = List.of(student1, student2);

        when(studentRepository.findAll()).thenReturn(students);
        when(gradeRepository.getGradeByStudent(student1)).thenReturn(grades1);
        when(gradeRepository.getGradeByStudent(student2)).thenReturn(grades2);

        //when
        Map<Long, Double> avgGrades = gradeService.getAvgGrades();
        //then
        assertThat(avgGrades.get(1L)).isEqualTo(3.50);
    }

    // getGradeByStudentId
    @Test
    void shouldReturnGradeDtoByStudentId(){
        //given
        Grade grade1 = mock(Grade.class);
        when(grade1.getGradeValue()).thenReturn(4);
        Grade grade2 = mock(Grade.class);
        when(grade2.getGradeValue()).thenReturn(3);
        List<Grade> grades = List.of(grade1, grade2);
        when(gradeRepository.getGradesByStudentId(1L)).thenReturn(grades);
        //when
        List<GradeDto> gradesByStudentId = gradeService.getGradeByStudentId(1L);
        //then
        assertThat(gradesByStudentId.get(0).getGradeValue()).isEqualTo(4);
        assertThat(gradesByStudentId.get(1).getGradeValue()).isEqualTo(3);
    }
    @Test
    void shouldReturnGradeDtoByStudentIdWith0Grade(){
        //given
        List<Grade> grades = List.of();
        when(gradeRepository.getGradesByStudentId(1L)).thenReturn(grades);
        //when
        List<GradeDto> gradesByStudentId = gradeService.getGradeByStudentId(1L);
        //then
        assertThat(gradesByStudentId.size()).isEqualTo(0);
    }

    @Test
    void shouldReturnCorrectGradeDtoByGradeId(){
        //given
        Grade grade = mock(Grade.class);
        when(grade.getGradeValue()).thenReturn(5);
        when(grade.getId()).thenReturn(1L);

        when(gradeRepository.getGradeById(1L)).thenReturn(grade);
        //when
        GradeDto gradeById = gradeService.getGradeById(1L);
        //then
        assertThat(grade.getGradeValue()).isEqualTo(gradeById.getGradeValue());
        assertThat(grade.getId()).isEqualTo(gradeById.getId());
    }



    //addGrade
    @Test
    void shouldSaveGradeWithCorrectValues(){
        //given
        Student student = mock(Student.class);
        Subject subject = mock(Subject.class);
        when(studentRepository.findStudentById(1L)).thenReturn(student);
        when(subjectRepository.getSubjectById(2L)).thenReturn(subject);
        ArgumentCaptor<Grade> captor = ArgumentCaptor.forClass(Grade.class);
        //when
        gradeService.addGrade(5,1L,2L);
        verify(gradeRepository).save(captor.capture());
        Grade grade = captor.getValue();
        //then
        assertThat(grade.getGradeValue()).isEqualTo(5);
        assertThat(grade.getStudent()).isEqualTo(student);
        assertThat(grade.getSubject()).isEqualTo(subject);
    }
    //deleteGrade
    @Test
    void shouldDeleteGradeById() {
        // given
        Long gradeId = 1L;
        // when
        gradeService.deleteGradeById(gradeId);
        // then
        verify(gradeRepository).deleteById(gradeId);
    }

}