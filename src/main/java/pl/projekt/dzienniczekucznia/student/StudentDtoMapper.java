package pl.projekt.dzienniczekucznia.student;

import pl.projekt.dzienniczekucznia.student.dto.StudentDto;

public class StudentDtoMapper {

    static StudentDto map(Student student){
        return new StudentDto(student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getPesel(),
                student.getSubjects(),
                student.getLogin(),
                student.getPassword());
    }
}
