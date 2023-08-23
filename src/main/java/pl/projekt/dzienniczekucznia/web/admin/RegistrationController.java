package pl.projekt.dzienniczekucznia.web.admin;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.projekt.dzienniczekucznia.student.StudentService;
import pl.projekt.dzienniczekucznia.student.dto.StudentRegistrationDto;
import pl.projekt.dzienniczekucznia.subject.SubjectService;
import pl.projekt.dzienniczekucznia.subject.dto.SubjectDto;
import pl.projekt.dzienniczekucznia.teacher.TeacherService;
import pl.projekt.dzienniczekucznia.teacher.dto.TeacherRegistrationDto;

@Controller
public class RegistrationController {
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final SubjectService subjectService;

    public RegistrationController(TeacherService teacherService, StudentService studentService, SubjectService subjectService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.subjectService = subjectService;
    }

    @GetMapping("/rejestracja")
    public String chooseWhomToRegistration(){
        return "registration";
    }
    @PostMapping("/rejestracja")
    public String registrationSelectedUser(String choose){
        if (choose.equals("teacher"))
            return "registration-teacher";
        else if (choose.equals("student"))
            return "registration-teacher";
        else
            return "404";
    }

    @GetMapping("/rejestracja/nauczyciel")
    public String registrationTeacher(Model model){
        TeacherRegistrationDto teacherRegistration = new TeacherRegistrationDto();
        model.addAttribute("teacher", teacherRegistration);
        return "registration-teacher";
    }

    @PostMapping("/rejestracja/nauczyciel")
    public String registerTeacher(@Valid TeacherRegistrationDto teacherRegistration,
                                  String subjectName,
                                  BindingResult bindingResult,
                                  Model model,
                                  HttpSession session){
        if (bindingResult.hasErrors()){
            model.addAttribute("errors", bindingResult.getFieldError());
            return "registration-teacher";
        }
        teacherService.registerTeacher(teacherRegistration);
        if (!isExist(subjectName)){
            createNewSubject(subjectName);
        }
        session.setAttribute("info", "Rejestracja zakończona sukcesem! Możesz się teraz zalogować.");
        return "redirect:/login";
    }

    @GetMapping("/rejestracja/uczen")
    public String registrationStudent(Model model){
        StudentRegistrationDto studentRegistration = new StudentRegistrationDto();
        model.addAttribute("student", studentRegistration);
        model.addAttribute("subjects", subjectService.getAll());
        return "registration-student";
    }
    @PostMapping("/rejestracja/uczen")
    public String registerStudent(StudentRegistrationDto studentRegistration,
                                  @RequestParam(value = "subject",required = false) Long[] selectedSubjectsIds,
                                  HttpSession session){
        studentService.registerStudent(studentRegistration, selectedSubjectsIds);
        session.setAttribute("info", "Rejestracja zakończona sukcesem! Możesz się teraz zalogować.");
        return "redirect:/login";
    }

    private void createNewSubject(String subjectName) {
        long teacherId = teacherService.getNewTeacherId();
        subjectService.addNewSubject(subjectName, teacherId);
    }

    private boolean isExist(String subjectName) {
        SubjectDto subjectByName = subjectService.getSubjectByName(subjectName);
        return subjectByName != null;

    }


}
