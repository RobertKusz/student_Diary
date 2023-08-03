package pl.projekt.dzienniczekucznia.subject;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Subject getSubjectById(Long id);
    List<Subject> findAll();

    Optional<Subject> getSubjectByName(String name);
}
