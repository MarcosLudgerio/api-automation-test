package br.edu.ufcg.virtus.courseautomation.controllers;


import br.edu.ufcg.virtus.courseautomation.dtos.UserDTO;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<List<UserApi>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}", produces = "application/json")
    public ResponseEntity<?> geUser(@RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(this.userService.findOne(token), HttpStatus.OK);
        } catch (UserApiException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/users", produces = "application/json")
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserApi userApi) {
        return new ResponseEntity<>(this.userService.createNewUser(userApi), HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{id}", produces = "application/json")
    public ResponseEntity<?> updateUserApi(@RequestHeader("Authorization") String token, @RequestBody UserApi userApi) {
        try{
            return new ResponseEntity<>(this.userService.updateUser(token, userApi), HttpStatus.OK);
        }catch(UserApiException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/users", produces = "application/json")
    public ResponseEntity<?> dropUser(@RequestHeader("Authorization") String token){
        try {
            return new ResponseEntity<>(this.userService.deleteUser(token), HttpStatus.OK);
        } catch (UserApiException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
