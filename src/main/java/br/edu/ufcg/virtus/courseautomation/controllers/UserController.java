package br.edu.ufcg.virtus.courseautomation.controllers;


import br.edu.ufcg.virtus.courseautomation.dtos.UserDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.UserWithoutPassDTO;
import br.edu.ufcg.virtus.courseautomation.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/users", produces = "application/json")
@Api(value = "API Rest Curso Automação")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "", produces = "application/json")
    @ApiOperation(value = "Retorna todos os usuários cadastrados")
    public ResponseEntity<List<UserWithoutPassDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "details", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retorna detalhes de um único usuário")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(this.userService.findOne(token), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    @ApiOperation(value = "Cadastra um novo usuário")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid UserDTO userDto) {
        return new ResponseEntity<>(this.userService.createNewUser(userService.fromDTO(userDto)), HttpStatus.CREATED);
    }

    @PutMapping(value = "", produces = "application/json")
    @ApiOperation(value = "Atualiza dados do usuário")
    public ResponseEntity<?> updateUserApi(@RequestHeader("Authorization") String token, @RequestBody @Valid UserDTO userDto) {
        return new ResponseEntity<>(this.userService.updateUser(token, userService.fromDTO(userDto)), HttpStatus.OK);
    }

}
