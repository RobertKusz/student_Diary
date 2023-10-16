package pl.projekt.dzienniczekucznia.behavioralNote;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import pl.projekt.dzienniczekucznia.behavioralNote.dto.BehavioralNoteDto;
import pl.projekt.dzienniczekucznia.student.Student;
import pl.projekt.dzienniczekucznia.student.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BehavioralNoteServiceTest {
    @Mock BehavioralNoteRepository behavioralNoteRepository;
    @Mock StudentRepository studentRepository;

    private BehavioralNoteService behavioralNoteService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        behavioralNoteService = new BehavioralNoteService(behavioralNoteRepository, studentRepository);
    }

    @Test
    void shouldSaveBehavioralNote(){
        //given
        BehavioralNote behavioralNote = new BehavioralNote();
        //when
        behavioralNoteService.addBehavioralNote(behavioralNote);
        //then
        verify(behavioralNoteRepository).save(behavioralNote);
    }

    @Test
    void shouldReturnListOfBehavioralNotes(){
        //given
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);

        BehavioralNote note1 = new BehavioralNote();
        note1.setStudent(student);
        BehavioralNote note2 = new BehavioralNote();
        note2.setStudent(student);

        List<BehavioralNote> behavioralNotes = new ArrayList<>();
        behavioralNotes.add(note1);
        behavioralNotes.add(note2);

        //when
        when(studentRepository.findStudentById(studentId)).thenReturn(student);
        when(behavioralNoteRepository.getAllByStudent(student)).thenReturn(behavioralNotes);
        List<BehavioralNoteDto> result = behavioralNoteService.getAllBehavioralNoteByStudentId(studentId);
        //then
        assertThat(result.size()).isEqualTo(2);
    }


}