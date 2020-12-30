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
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserApi>> getAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserApi> geUser(@PathVariable Long id) throws UserApiException {
        return new ResponseEntity<>(userService.findOne(id).orElseThrow(() -> new UserApiException("Usuário não encontrado, tente novamente")), HttpStatus.OK);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserApi> createNewUser(@RequestBody UserApi userApi){
        return new ResponseEntity<UserApi>(this.userService.createNewUser(userApi), HttpStatus.CREATED);
    }

    @PutMapping(value = "/users")
    public ResponseEntity<UserApi> updateUserApi(@RequestBody UserApi userApi) throws UserApiException {
        return new ResponseEntity<UserApi>(this.userService.updateUser(userApi), HttpStatus.OK);
    }

    @DeleteMapping (value = "/users")
    public ResponseEntity<Optional<UserApi> > dropUser(@RequestBody Long idUser) throws UserApiException {
        return new ResponseEntity<>(this.userService.deleteUser(idUser), HttpStatus.OK);
    }

}
