package pl.projekt.dzienniczekucznia.web.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.projekt.dzienniczekucznia.grades.GradeService;
import pl.projekt.dzienniczekucznia.grades.dto.GradeDto;
import pl.projekt.dzienniczekucznia.student.StudentService;
import pl.projekt.dzienniczekucznia.student.dto.StudentDto;
import pl.projekt.dzienniczekucznia.subject.SubjectService;
import pl.projekt.dzienniczekucznia.subject.dto.SubjectDto;

import java.util.List;

@Controller
public class TeacherPanelController {
    private final StudentService studentService;
    private final GradeService gradeService;
    private final SubjectService subjectService;

    public TeacherPanelController(StudentService studentService, GradeService gradeService, SubjectService subjectService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
        this.subjectService = subjectService;
    }

    @GetMapping("/panel-nauczyciela/nowa-ocena")
    String newGrade(Model model){
        List<StudentDto> students = studentService.getAllStudents();
        List<SubjectDto> subjects = subjectService.getAll();
        StudentDto newStudent = new StudentDto();
        model.addAttribute("students", students);
        model.addAttribute("newStudent", newStudent);
        model.addAttribute("subjects", subjects);
        return "new-grade";
    }

    @PostMapping("/panel-nauczyciela/nowa-ocena")
    String addGrade (int gradeValue, Long subjectId, Long studentId){
        gradeService.addGrade(gradeValue,studentId,subjectId);
        return "redirect:/panel-nauczyciela";
    }
}
