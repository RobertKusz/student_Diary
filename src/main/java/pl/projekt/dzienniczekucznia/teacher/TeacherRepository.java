package pl.projekt.dzienniczekucznia.teacher;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.projekt.dzienniczekucznia.teacher.dto.TeacherDto;

import java.util.Optional;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    Teacher getTeacherByLogin(String login);
    long count();
    Optional<Teacher> getTeacherById(long id);

    Optional<Teacher> findTopByOrderByIdDesc();
}
