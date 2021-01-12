package br.edu.ufcg.virtus.courseautomation.controllers.exceptions;

import br.edu.ufcg.virtus.courseautomation.exceptions.PostException;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserAlreadyExistsException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(UserApiException.class)
    public static ResponseEntity<StandardError> noPrivilegesForThat(UserApiException e, String uri) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Not Privileges", e.getMessage(), uri);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(TokenException.class)
    public static ResponseEntity<StandardError> invalidToken(TokenException e, String uri) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Unauthorized", e.getMessage(), uri);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(PostException.class)
    public static ResponseEntity<StandardError> postNotFound(PostException e, String uri) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Not Found", e.getMessage(), uri);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public static ResponseEntity<StandardError> userAlreadyExists(Exception e, String uri) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Conflict", e.getMessage(), uri);
        return ResponseEntity.status(status).body(err);
    }

}
