package br.edu.ufcg.virtus.courseautomation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class CourseAutomationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseAutomationApplication.class, args);
    }

    @GetMapping("/")
    @ResponseBody
    String index() {
        return "<h1> API propria para testes! </h1>";
    }


}
