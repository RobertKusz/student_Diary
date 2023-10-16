package pl.projekt.dzienniczekucznia.grades;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projekt.dzienniczekucznia.grades.dto.GradeDto;
import pl.projekt.dzienniczekucznia.student.Student;
import pl.projekt.dzienniczekucznia.student.StudentRepository;
import pl.projekt.dzienniczekucznia.subject.Subject;
import pl.projekt.dzienniczekucznia.subject.SubjectRepository;

import java.util.*;


@Service
public class GradeService {
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public Map<Long,Double> getAvgGrades(){
        List<Student> students = studentRepository.findAll();
            Map<Long,Double> avgGrads = new HashMap<>();
        for (Student student : students) {
            List<Integer> studentGrades = gradeRepository.getGradeByStudent(student)
                    .stream().
                    map(Grade::getGradeValue).
                    toList();
            avgGrads.put(student.getId(), getAvgFromList(studentGrades));
        }
        return avgGrads;
    }

    private double getAvgFromList(List<Integer> studentGrades) {
        double sum = 0;
        for (Integer studentGrade : studentGrades) {
            sum += studentGrade;
        }
        double avgGrade = sum/studentGrades.size();
        return (double) Math.round(avgGrade * 100) / 100;
    }

    public List<GradeDto> getGradeByStudentId(Long id){
        return gradeRepository.getGradesByStudentId(id)
                .stream()
                .map(GradeDtoMapper::map)
                .toList();
    }
    public GradeDto getGradeById(Long id){
        Grade grade = gradeRepository.getGradeById(id);
        return GradeDtoMapper.map(grade);
    }

    @Transactional
    public void addGrade(int gradeValue, Long studentId, Long subjectId){
        Grade gradeToSave = new Grade();
        Student student = studentRepository.findStudentById(studentId);
        Subject subject = subjectRepository.getSubjectById(subjectId);

        gradeToSave.setGradeValue(gradeValue);
        gradeToSave.setStudent(student);
        gradeToSave.setSubject(subject);
        gradeRepository.save(gradeToSave);
    }

    @Transactional
    public void deleteGradeById(Long gradeId) {
        gradeRepository.deleteById(gradeId);
    }
}