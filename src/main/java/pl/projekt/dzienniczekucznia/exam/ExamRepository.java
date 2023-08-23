package pl.projekt.dzienniczekucznia.exam;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.projekt.dzienniczekucznia.exam.dto.ExamDto;
import pl.projekt.dzienniczekucznia.subject.Subject;

import java.util.List;

@Repository
interface ExamRepository extends CrudRepository<Exam,Long> {
    List<Exam> getExamsBySubject(Subject subject);
}
