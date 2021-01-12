package br.edu.ufcg.virtus.courseautomation.controllers.exceptions;

import br.edu.ufcg.virtus.courseautomation.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationAdviceController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleUserApiException(UserAlreadyExistsException ex) {
        return new ApiErros(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErros handleValidationException(MethodArgumentNotValidException ex) {
        return new ApiErros(ex.getBindingResult().getAllErrors().stream().map((erro) -> erro.getDefaultMessage()).collect(Collectors.toList()));
    }
}
