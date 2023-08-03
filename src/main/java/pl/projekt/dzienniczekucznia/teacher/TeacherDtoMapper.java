package pl.projekt.dzienniczekucznia.teacher;

import pl.projekt.dzienniczekucznia.teacher.dto.TeacherDto;

public class TeacherDtoMapper {
    static TeacherDto map (Teacher teacher){
        return new TeacherDto(teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getSubject(),
                teacher.getLogin(),
                teacher.getPassword());
    }
}
