package pl.projekt.dzienniczekucznia.grades;

import pl.projekt.dzienniczekucznia.grades.dto.GradeDto;

public class GradeDtoMapper {
    public static GradeDto map(Grade grade){
        return new GradeDto(grade.getId(),
                grade.getGradeValue(),
                grade.getStudent(),
                grade.getSubject());
    }
}