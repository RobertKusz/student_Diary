package pl.projekt.dzienniczekucznia.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.projekt.dzienniczekucznia.student.StudentService;

@Controller
public class HomeController {

    private final StudentService studentService;

    public HomeController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String home(Model model){
        long userNumber = studentService.getUserNumber();
        model.addAttribute("studentNumber", userNumber);
        return "home-page";
    }

}
