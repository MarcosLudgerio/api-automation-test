package br.edu.ufcg.virtus.courseautomation.controllers;

import br.edu.ufcg.virtus.courseautomation.dtos.UserDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.UserLoginDTO;
import br.edu.ufcg.virtus.courseautomation.exceptions.HandleException;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenException;
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

    @PostMapping(value = "/auth/login", produces = "application/json")
    public ResponseEntity<?> authentication(@RequestBody UserLoginDTO userDTO) {
        try {
            return new ResponseEntity<>(jwtService.autentication(userDTO), HttpStatus.OK);
        } catch (UserApiException ex) {
            return new ResponseEntity<>(HandleException.noPrivilegesForThat(ex, "/auth/login").getBody(), HttpStatus.UNAUTHORIZED);
        } catch (TokenException e) {
            return new ResponseEntity<>(HandleException.invalidToken(e, "/auth/login").getBody(), HttpStatus.UNAUTHORIZED);
        }
    }
}
