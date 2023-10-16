package pl.projekt.dzienniczekucznia.behavioralNote;

import org.springframework.stereotype.Service;
import pl.projekt.dzienniczekucznia.behavioralNote.dto.BehavioralNoteDto;
import pl.projekt.dzienniczekucznia.student.Student;
import pl.projekt.dzienniczekucznia.student.StudentRepository;

import java.util.List;

@Service
public class BehavioralNoteService {
    private final BehavioralNoteRepository behavioralNoteRepository;
    private final StudentRepository studentRepository;

    public BehavioralNoteService(BehavioralNoteRepository behavioralNoteRepository, StudentRepository studentRepository) {
        this.behavioralNoteRepository = behavioralNoteRepository;
        this.studentRepository = studentRepository;
    }

    public void addBehavioralNote(BehavioralNote behavioralNote) {
        behavioralNoteRepository.save(behavioralNote);
    }

    public List<BehavioralNoteDto> getAllBehavioralNoteByStudentId(Long studentId) {
        Student student = studentRepository.findStudentById(studentId);
        return behavioralNoteRepository
                .getAllByStudent(student)
                .stream()
                .map(BehavioralNoteDtoMapper::map).
                toList();
    }
}