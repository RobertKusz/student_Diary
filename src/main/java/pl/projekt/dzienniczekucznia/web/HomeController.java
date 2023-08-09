package pl.projekt.dzienniczekucznia.web;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.projekt.dzienniczekucznia.homePage.WeatherService;
import pl.projekt.dzienniczekucznia.student.StudentService;

import java.util.Optional;

@Controller
public class HomeController {

    private final StudentService studentService;
    private final WeatherService weatherService;

    public HomeController(StudentService studentService, WeatherService weatherService) {
        this.studentService = studentService;
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String home(Model model){
        long userNumber = studentService.getUserNumber();
        Optional<Double> temperatureOptional = weatherService.getWeatherTemperature();
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