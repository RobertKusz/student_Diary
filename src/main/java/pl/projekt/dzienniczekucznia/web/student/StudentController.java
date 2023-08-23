package pl.projekt.dzienniczekucznia.web.student;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import pl.projekt.dzienniczekucznia.behavioralNote.BehavioralNote;
import pl.projekt.dzienniczekucznia.behavioralNote.BehavioralNoteService;
import pl.projekt.dzienniczekucznia.behavioralNote.dto.BehavioralNoteDto;
import pl.projekt.dzienniczekucznia.grades.GradeService;
import pl.projekt.dzienniczekucznia.student.StudentService;
import pl.projekt.dzienniczekucznia.student.dto.StudentDto;

import java.util.List;
import java.util.Map;

@Controller()
public class StudentController {

    private final StudentService studentService;
    private final GradeService gradeService;
    private final BehavioralNoteService behavioralNoteService;

    public StudentController(StudentService studentService, GradeService gradeService, BehavioralNoteService behavioralNoteService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
        this.behavioralNoteService = behavioralNoteService;
    }

    @GetMapping("/spis-studentow")
    private String getAllStudent(Model model){
        List<StudentDto> allStudents = studentService.getAllStudents();
        Map<Long, Double> avgGrades = gradeService.getAvgGrades();
        model.addAttribute("heading", "Spis wszystkich uczniów");
        model.addAttribute("students", allStudents);
        model.addAttribute("avgGrade", avgGrades);
        model.addAttribute("studentNumber", studentService.getUserNumber());
        return "students-list";
    }
    @GetMapping("/student/{id}")
    private String getStudentData(Model model,
                               @PathVariable Long id ){
        StudentDto student = studentService.getStudentById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("student", student);
        model.addAttribute("allBehavioralNotes", studentService.getBehavioralNotes());
        return "student-panel";
    }
    @GetMapping("/student/{id}/oceny")
    private String getStudentGrades(Model model,
                                  @PathVariable Long id ){
        StudentDto student = studentService.getStudentById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("allGrades", gradeService.getGradeByStudentId(id));
        model.addAttribute("student", student);
        model.addAttribute("studentNumber",studentService.getUserNumber());
        model.addAttribute("allBehavioralNotes", studentService.getBehavioralNotes());
        return "student-grades";
    }

    @GetMapping("/student/{studentId}/uwagi")
    private String getStudentBehavioralNote(@PathVariable Long studentId, Model model){
        List<BehavioralNoteDto> allBehavioralNote = behavioralNoteService.getAllBehavioralNoteByStudentId(studentId);
        model.addAttribute("behavioralNotes",allBehavioralNote);
        return "studentBehavioralNote";
    }

}