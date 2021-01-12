package br.edu.ufcg.virtus.courseautomation.controllers;

import br.edu.ufcg.virtus.courseautomation.controllers.exceptions.ExceptionHandle;
import br.edu.ufcg.virtus.courseautomation.dtos.UserLoginDTO;
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
            return new ResponseEntity<>(ExceptionHandle.noPrivilegesForThat(ex, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        } catch (TokenException e) {
            return new ResponseEntity<>(ExceptionHandle.invalidToken(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        }
    }
}
