package br.edu.ufcg.virtus.courseautomation.controllers;


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

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserApi>> getAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<?> geUser(@PathVariable Long id) {
        try{
            return new ResponseEntity<>(this.userService.findOne(id), HttpStatus.OK);
        }catch (UserApiException exception){
            return ResponseEntity.ok().body(exception.getMessage());
        }

    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserApi> createNewUser(@RequestBody UserApi userApi){
        return new ResponseEntity<>(this.userService.createNewUser(userApi), HttpStatus.CREATED);
    }

    @PutMapping(value = "/users")
    public ResponseEntity<UserApi> updateUserApi(@RequestBody UserApi userApi) throws UserApiException {
        return new ResponseEntity<>(this.userService.updateUser(userApi), HttpStatus.OK);
    }

    @DeleteMapping (value = "/users/{id}")
    public ResponseEntity<UserApi> dropUser(@PathVariable Long idUser) throws UserApiException {
        return new ResponseEntity<>(this.userService.deleteUser(idUser), HttpStatus.OK);
    }

}
