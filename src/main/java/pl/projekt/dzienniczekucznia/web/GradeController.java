package pl.projekt.dzienniczekucznia.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.projekt.dzienniczekucznia.grades.GradeService;
import pl.projekt.dzienniczekucznia.grades.dto.GradeDto;
import pl.projekt.dzienniczekucznia.student.StudentService;
import pl.projekt.dzienniczekucznia.student.dto.StudentDto;
import pl.projekt.dzienniczekucznia.subject.Subject;
import pl.projekt.dzienniczekucznia.subject.SubjectService;
import pl.projekt.dzienniczekucznia.teacher.Teacher;

@Controller
public class GradeController {
    private final StudentService studentService;
    private final GradeService gradeService;


    public GradeController(StudentService studentService, GradeService gradeService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    @GetMapping("/student/{student_id}/grade/{grade_id}")
    public String gradeInformation(Model model,
                                   @PathVariable Long student_id,
                                   @PathVariable Long grade_id){
        StudentDto student = studentService.getStudentById(student_id).orElseThrow();
        GradeDto grade = gradeService.getGradeById(grade_id);
        Subject subject = grade.getSubject();
        Teacher teacher = subject.getTeacher();

        model.addAttribute("student", student);
        model.addAttribute("grade",grade);
        model.addAttribute("subject", subject);
        model.addAttribute("teacher", teacher);

        return "grade";
    }
}
