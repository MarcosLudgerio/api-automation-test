package br.edu.ufcg.virtus.courseautomation;

import br.edu.ufcg.virtus.courseautomation.dtos.UserDTO;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@Controller
@SpringBootApplication
public class CourseAutomationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseAutomationApplication.class, args);
    }

    @GetMapping(value = "/", produces = "text/html")
    public String index() {
        return "post.html";
    }
}
