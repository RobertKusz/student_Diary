package pl.projekt.dzienniczekucznia.web.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.projekt.dzienniczekucznia.behavioralNote.BehavioralNote;
import pl.projekt.dzienniczekucznia.behavioralNote.BehavioralNoteService;
import pl.projekt.dzienniczekucznia.grades.GradeService;
import pl.projekt.dzienniczekucznia.grades.dto.GradeDto;
import pl.projekt.dzienniczekucznia.student.StudentService;
import pl.projekt.dzienniczekucznia.student.dto.StudentDto;
import pl.projekt.dzienniczekucznia.subject.SubjectService;
import pl.projekt.dzienniczekucznia.subject.dto.SubjectDto;
import pl.projekt.dzienniczekucznia.teacher.Teacher;
import pl.projekt.dzienniczekucznia.teacher.TeacherService;

import java.util.List;

@Controller
public class TeacherPanelController {
    private final StudentService studentService;
    private final GradeService gradeService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final BehavioralNoteService behavioralNoteService;

    public TeacherPanelController(StudentService studentService, GradeService gradeService, SubjectService subjectService, TeacherService teacherService, BehavioralNoteService behavioralNoteService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.behavioralNoteService = behavioralNoteService;
    }
    @GetMapping("/panel-nauczyciela")
    String teacherPanel(){
        return "teacher-panel";
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

    @GetMapping("/panel-nauczyciela/usuwanie-oceny")
    String deleteGradeChoice(Model model){
        List<StudentDto> students = studentService.getAllStudents();
        model.addAttribute("students", students);

        return "delete-grade";
    }
    @GetMapping("/panel-nauczyciela/usuwanie-oceny/{id}")
    String deleteGradeSelectedStudent(Model model, @PathVariable Long id){
        StudentDto student = studentService.getStudentById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<GradeDto> gradeByStudentId = gradeService.getGradeByStudentId(student.getId());
        model.addAttribute("student",student);
        model.addAttribute("allGrades",gradeByStudentId);
        return "delete-grade-selected-student";
    }
    @GetMapping("/panel-nauczyciela/usuwanie-oceny/{studentId}/{gradeId}")
    String deleteGradeSelectedGrade(Model model, @PathVariable Long gradeId,@PathVariable Long studentId){
        model.addAttribute("gradeId", gradeId);
        model.addAttribute("studentId", studentId);
        return "delete-grade-selected-grade";
    }

    @PostMapping("/panel-nauczyciela/usuwanie-oceny/{studentId}/{gradeId}")
    String deleteGrade(@PathVariable Long gradeId){
        gradeService.deleteGradeById(gradeId);
        return "redirect:/panel-nauczyciela/usuwanie-oceny";
    }

    @GetMapping("/panel-nauczyciela/nowa-uwaga")
    String addBehavioralNote(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Teacher authenticatedTeacher = teacherService.getByLogin(authentication.getName());
        BehavioralNote behavioralNote = new BehavioralNote();
        List<StudentDto> allStudents = studentService.getAllStudents();
        model.addAttribute("authenticatedTeacher",authenticatedTeacher);
        model.addAttribute("behavioralNote", behavioralNote);
        model.addAttribute("students", allStudents);
        return "behavioral-note";
    }

    @PostMapping("/panel-nauczyciela/nowa-uwaga")
    String addBehavioralNote(BehavioralNote behavioralNote){
        behavioralNoteService.addBehavioralNote(behavioralNote);

        return "redirect:/panel-nauczyciela";
    }

    @GetMapping("/panel-nauczyciela/nowy-sprawdzian")
    String addTest(){
        return"new-test";
    }{}

}
