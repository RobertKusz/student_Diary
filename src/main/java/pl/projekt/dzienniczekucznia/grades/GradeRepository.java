package pl.projekt.dzienniczekucznia.grades;

import org.springframework.data.repository.CrudRepository;
import pl.projekt.dzienniczekucznia.student.Student;

import java.util.List;

public interface GradeRepository extends CrudRepository<Grade, Long> {
    List<Grade> findAll();
    List<Grade> getGradeByStudent(Student student);
    List<Grade> getGradesByStudentId(Long id);
    Grade getGradeById(Long id);
}
