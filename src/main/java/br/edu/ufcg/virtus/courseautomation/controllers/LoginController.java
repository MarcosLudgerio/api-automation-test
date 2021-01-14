package br.edu.ufcg.virtus.courseautomation.controllers;

import br.edu.ufcg.virtus.courseautomation.dtos.UserLoginDTO;
import br.edu.ufcg.virtus.courseautomation.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    @Autowired
    private JWTService jwtService;

    @PostMapping(value = "/auth/login", produces = "application/json")
    public ResponseEntity<?> authentication(@RequestBody @Valid UserLoginDTO userDTO) {
        return new ResponseEntity<>(jwtService.autentication(userDTO), HttpStatus.OK);
    }
}
