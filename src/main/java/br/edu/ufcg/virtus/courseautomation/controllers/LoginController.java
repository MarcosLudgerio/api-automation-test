package br.edu.ufcg.virtus.courseautomation.controllers;

import br.edu.ufcg.virtus.courseautomation.dtos.UserDTO;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private JWTService jwtService;

    @PostMapping(value = "/auth/login")
    public ResponseEntity<String> authentication(@RequestBody UserDTO userDTO) throws UserApiException {
        return new ResponseEntity<>(jwtService.autentication(userDTO), HttpStatus.OK);
    }
}
