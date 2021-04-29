package br.edu.ufcg.virtus.courseautomation.controllers;

import br.edu.ufcg.virtus.courseautomation.dtos.userViewDTO.UserViewDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserWithoutPassDTO;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "details", produces = "text/html")
    public ModelAndView getOneUserView(@RequestParam(value="email") String email) {
        ModelAndView mv = new ModelAndView("viewUniqueUser");
        UserViewDTO userViewDTO = new UserViewDTO(this.userService.findByEmail(email));
        String src = userViewDTO.getUrlImage();
        mv.addObject("userView", userViewDTO);
        mv.addObject("src", src);
        mv.addObject("website", userViewDTO.getEmail());
        return mv;
    }

    @GetMapping(value = "new", produces = "text/html")
    public ModelAndView createNewUser() {
        ModelAndView mv = new ModelAndView("newUser");
        mv.addObject("userData", new UserApi());
        return mv;
    }

    @RequestMapping(value = "/newpost",method = RequestMethod.POST)
    public ModelAndView createNewUser(UserApi user) {
        ModelAndView mv = new ModelAndView("newUser");
        mv.addObject("userData", new UserApi());
        userService.createNewUser(user);
        return mv;
    }
}
