package pl.projekt.dzienniczekucznia.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.projekt.dzienniczekucznia.exam.ExamService;
import pl.projekt.dzienniczekucznia.exam.dto.ExamDto;
import pl.projekt.dzienniczekucznia.homePage.WeatherService;
import pl.projekt.dzienniczekucznia.student.StudentService;
import pl.projekt.dzienniczekucznia.student.dto.StudentDto;
import pl.projekt.dzienniczekucznia.teacher.TeacherService;
import pl.projekt.dzienniczekucznia.teacher.dto.TeacherDto;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final StudentService studentService;
    private final WeatherService weatherService;
    private final TeacherService teacherService;
    private final ExamService examService;
    private final HomeService homeService;

    public HomeController(StudentService studentService, WeatherService weatherService, TeacherService teacherService, ExamService examService, HomeService homeService) {
        this.studentService = studentService;
        this.weatherService = weatherService;
        this.teacherService = teacherService;
        this.examService = examService;
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String home(Model model){
        long userNumber = studentService.getUserNumber();
        Optional<Double> temperatureOptional = weatherService.getWeatherTemperature();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = homeService.getRole(authentication.getName());
        if (role.equals("STUDENT")){
            StudentDto student =  studentService.getByLogin(authentication.getName()).orElseThrow();
            List<ExamDto> examsByStudent = examService.getExamsByStudentSubject(student.getSubjects());
            model.addAttribute("student", student);
            model.addAttribute("examsByStudent", examsByStudent);

        } else if (role.equals("TEACHER")) {
            TeacherDto teacher =  teacherService.getByLogin(authentication.getName()).orElseThrow();
            List<ExamDto> examsByTeacher = examService.getExamsByTeacherSubject(teacher.getSubject());
            model.addAttribute("teacher", teacher);
            model.addAttribute("examsByTeacher", examsByTeacher);
        }

        if (temperatureOptional.isPresent()) {
            Double temperature = temperatureOptional.get();
            String thermometer = studentService.getThermometerValue(temperature);
            model.addAttribute("temperature", temperature);
            model.addAttribute("thermometerValue", thermometer);
        } else {
            model.addAttribute("temperature", "Brak danych o temperaturze.");
        }
        model.addAttribute("studentNumber", userNumber);
        return "home-page";
    }
}