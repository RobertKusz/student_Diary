package pl.projekt.dzienniczekucznia.behavioralNote;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.projekt.dzienniczekucznia.student.Student;

import java.util.List;

@Repository
public interface BehavioralNoteRepository extends CrudRepository<BehavioralNote, Long> {
    List<BehavioralNote> findAll();

    List<BehavioralNote> getAllByStudent(Student student);
}
