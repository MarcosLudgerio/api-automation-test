package br.edu.ufcg.virtus.courseautomation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleException {

    @ExceptionHandler(UserApiException.class)
    public static ResponseEntity<StandardError> noPrivilegesForThat(UserApiException e, String uri){
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Usuário não tem privilégios para ação", e.getMessage(), uri);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(TokenException.class)
    public static ResponseEntity<StandardError> invalidToken(TokenException e, String uri){
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Token inválido, refaça login e tente novanemte", e.getMessage(), uri);
        return ResponseEntity.status(status).body(err);
    }

}
