package br.edu.ufcg.virtus.courseautomation.controllers;

import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserWithoutPassDTO;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/users", produces = "application/json")
@Api(value = "API Rest Curso Automação")
@CrossOrigin(origins = "*")
public class ViewUserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "", produces = "text/html")
    public ModelAndView getAllUsersView() {
        ModelAndView mv = new ModelAndView("viewAllUsers");
        List<UserWithoutPassDTO> userServiceAllUsers = this.userService.findAllUsers();
        mv.addObject("users", userServiceAllUsers);
        return mv;
    }

    @GetMapping(value = "oneUser", produces = "text/html")
    public ModelAndView getOneUserView() {
        ModelAndView mv = new ModelAndView("erros");
        UserApi userServiceAllUsers = this.userService.findByEmail("raimundo@dcx.ufpb.br");
        mv.addObject("user", userServiceAllUsers);
        return mv;
    }

    @GetMapping(value = "newuser", produces = "text/html")
    public ModelAndView createNewUser() {
        ModelAndView mv = new ModelAndView("newUser");
        return mv;
    }
}
