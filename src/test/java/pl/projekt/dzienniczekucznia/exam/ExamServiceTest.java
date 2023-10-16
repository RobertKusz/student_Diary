package pl.projekt.dzienniczekucznia.exam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import pl.projekt.dzienniczekucznia.exam.dto.ExamDto;
import pl.projekt.dzienniczekucznia.subject.Subject;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ExamServiceTest {

    @Mock ExamRepository examRepository;
    private ExamService examService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        examService = new ExamService(examRepository);
    }

    @Test
    void shouldAddExam(){
        //given
        Exam exam = new Exam();
        String dateString = "2023-02-11";
        //when
        examService.addExam(exam,dateString);
        //then
        verify(examRepository).save(exam);
    }

    @Test
    void shouldReturnListOfExamDto(){
        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Matematyka");

        Exam exam1 = new Exam();
        Exam exam2 = new Exam();
        //when
        when(examRepository.getExamsBySubject(subject)).thenReturn(List.of(exam1,exam2));

        List<ExamDto> examsByTeacherSubject = examService.getExamsByTeacherSubject(subject);
        //then
        assertThat(examsByTeacherSubject.size()).isEqualTo(2);
    }
    @Test
    void shouldReturnListOfExamDtoWith0Exam(){
        //given
        Subject subject = mock(Subject.class);
        //when
        when(examRepository.getExamsBySubject(subject)).thenReturn(List.of());

        List<ExamDto> examsByTeacherSubject = examService.getExamsByTeacherSubject(subject);
        //then
        assertThat(examsByTeacherSubject.size()).isEqualTo(0);
    }

    @Test
    void shouldReturnListWithAllExamsByStudentSubjects(){
        //given
        Subject subject1 = mock(Subject.class);
        Exam exam1 = mock(Exam.class);
        Exam exam2 = mock(Exam.class);
        List<Exam> exams1 = List.of(exam1, exam2);
        when(examRepository.getExamsBySubject(subject1)).thenReturn(exams1);

        Subject subject2 = mock(Subject.class);
        Exam exam3 = mock(Exam.class);
        Exam exam4 = mock(Exam.class);
        List<Exam> exams2 = List.of(exam3, exam4);
        when(examRepository.getExamsBySubject(subject2)).thenReturn(exams2);
        List<Subject> subjects = List.of(subject1, subject2);

        //when
        List<ExamDto> result = examService.getExamsByStudentSubject(subjects);

        //then
        assertThat(result.size()).isEqualTo(4);
    }
    @Test
    void shouldReturnListWithAllExamsByStudentSubjectsWith0Exam(){
        //given
        List<Subject> subjects = List.of();
        //when
        List<ExamDto> result = examService.getExamsByStudentSubject(subjects);

        //then
        assertThat(result.size()).isEqualTo(0);
    }

}