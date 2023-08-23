package pl.projekt.dzienniczekucznia.exam;

import pl.projekt.dzienniczekucznia.exam.dto.ExamDto;

public class ExamDtoMapper {
    public static ExamDto map(Exam exam){
        return new ExamDto(
                exam.getId(),
                exam.getDate(),
                exam.getDescription(),
                exam.getTeacher(),
                exam.getSubject());
    }
}
