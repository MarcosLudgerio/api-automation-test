package br.edu.ufcg.virtus.courseautomation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleException {

    @ExceptionHandler(UserApiException.class)
    public static ResponseEntity<StandardError> noPrivilegesForThat(UserApiException e, String uri) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Not Privileges", e.getMessage(), "http://localhost:8080/" + uri);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(TokenException.class)
    public static ResponseEntity<StandardError> invalidToken(TokenException e, String uri) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Unauthorized", e.getMessage(), "http://localhost:8080/" + uri);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(PostException.class)
    public static ResponseEntity<StandardError> postNotFound(PostException e, String uri) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Not Found", e.getMessage(), "http://localhost:8080/" + uri);
        return ResponseEntity.status(status).body(err);
    }


}
