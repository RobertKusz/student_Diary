package pl.projekt.dzienniczekucznia.student;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student,Long> {

    List<Student> findAll();
    Optional<Student> findById(Long id);
    Student findStudentByLogin(String login);
    Student findStudentById(Long id);


}
