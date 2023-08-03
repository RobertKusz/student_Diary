package pl.projekt.dzienniczekucznia.subject;

import org.springframework.stereotype.Service;
import pl.projekt.dzienniczekucznia.subject.dto.SubjectDto;
import pl.projekt.dzienniczekucznia.teacher.TeacherService;

import java.util.List;

@Service
public class SubjectService {
        private final SubjectRepository subjectRepository;
        private final TeacherService teacherService;

    public SubjectService(SubjectRepository subjectRepository, TeacherService teacherService) {
        this.subjectRepository = subjectRepository;
        this.teacherService = teacherService;
    }

    public SubjectDto getSubjectById(Long id){
        Subject subject = subjectRepository.getSubjectById(id);
        return SubjectDtoMapper.map(subject);
    }

    public List<SubjectDto> getAll(){
        return subjectRepository
                .findAll()
                .stream()
                .map(SubjectDtoMapper::map)
                .toList();
    }

    public SubjectDto getSubjectByName(String subjectName){
        return subjectRepository.getSubjectByName(subjectName).map(SubjectDtoMapper::map).orElse(null);
    }

    public void addNewSubject(String subjectName, long teacherId) {
        Subject subject = new Subject();
        subject.setName(subjectName);
        subject.setTeacher(teacherService.getTeacherById(teacherId));
        subjectRepository.save(subject);
    }
}
