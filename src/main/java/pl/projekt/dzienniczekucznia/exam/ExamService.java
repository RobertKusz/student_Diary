package pl.projekt.dzienniczekucznia.exam;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.projekt.dzienniczekucznia.exam.dto.ExamDto;
import pl.projekt.dzienniczekucznia.subject.Subject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExamService {
    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Transactional
    public void addExam(Exam exam, String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date convertedDate = dateFormat.parse(dateString);
            exam.setDate(convertedDate);
            examRepository.save(exam);

        } catch (ParseException e) {
            System.out.println("błąd konwersi daty "+ e.getMessage());
        }
    }

    public List<ExamDto> getExamsByTeacherSubject(Subject subject) {
        return examRepository.getExamsBySubject(subject)
                .stream()
                .map(ExamDtoMapper::map)
                .toList();
    }

    public List<ExamDto> getExamsByStudentSubject(List<Subject> subjects) {
        List<Exam> exams = new ArrayList<>();
        for (Subject subject : subjects) {
            exams.addAll(examRepository.getExamsBySubject(subject));
        }
        return exams
                .stream()
                .map(ExamDtoMapper::map)
                .toList();
    }

}