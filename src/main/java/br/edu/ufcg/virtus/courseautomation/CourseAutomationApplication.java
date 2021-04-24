package br.edu.ufcg.virtus.courseautomation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class CourseAutomationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseAutomationApplication.class, args);
    }

    public String index() {
        return "post.html";
    }
}
