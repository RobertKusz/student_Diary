package pl.projekt.dzienniczekucznia.subject;

import pl.projekt.dzienniczekucznia.subject.dto.SubjectDto;

public class SubjectDtoMapper {
    static SubjectDto map(Subject subject){
        return new SubjectDto(subject.getId(),
                subject.getName(),
                subject.getDescription(),
                subject.getTeacher());
    }

    public static Subject map(SubjectDto subjectDto){
        Subject subject = new Subject();
        subject.setId(subjectDto.getId());
        subject.setName(subjectDto.getName());
        subject.setDescription(subjectDto.getDescription());
        subject.setTeacher(subjectDto.getTeacher());
        return subject;
    }
}
