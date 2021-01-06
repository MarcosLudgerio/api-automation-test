package br.edu.ufcg.virtus.courseautomation.controllers;


import br.edu.ufcg.virtus.courseautomation.dtos.UserDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.UserWithoutIdDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.UserWithoutPassDTO;
import br.edu.ufcg.virtus.courseautomation.exceptions.HandleException;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserAlreadyExistsException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        try {
            return new ResponseEntity<>(this.userService.findOne(token), HttpStatus.OK);
        } catch (UserApiException exception) {
            return new ResponseEntity<>(HandleException.noPrivilegesForThat(exception, "users").getBody(), HttpStatus.UNAUTHORIZED);
        } catch (TokenException e) {
            return new ResponseEntity<>(HandleException.invalidToken(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "", produces = "application/json")
    @ApiOperation(value = "Cadastra um novo usuário")
    public ResponseEntity<?> createNewUser(@RequestBody UserWithoutIdDTO userApi) {
        System.out.println(" userAPI " + userApi);
        try {
            return new ResponseEntity<>(this.userService.createNewUser(userApi), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HandleException.userAlreadyExists(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "", produces = "application/json")
    @ApiOperation(value = "Atualiza dados do usuário")
    public ResponseEntity<?> updateUserApi(@RequestHeader("Authorization") String token, @RequestBody UserApi userApi) {
        try {
            return new ResponseEntity<>(this.userService.updateUser(token, userApi), HttpStatus.OK);
        } catch (UserApiException ex) {
            return new ResponseEntity<>(HandleException.noPrivilegesForThat(ex, "").getBody(), HttpStatus.UNAUTHORIZED);
        } catch (TokenException e) {
            return new ResponseEntity<>(HandleException.invalidToken(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HandleException.userAlreadyExists(e, "/users").getBody(), HttpStatus.BAD_REQUEST);
        }
    }

}
