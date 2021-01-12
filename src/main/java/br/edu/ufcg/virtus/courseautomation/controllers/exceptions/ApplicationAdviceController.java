package br.edu.ufcg.virtus.courseautomation.controllers.exceptions;

import br.edu.ufcg.virtus.courseautomation.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationAdviceController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErros handleUserApiException(UserAlreadyExistsException ex) {
        return new ApiErros(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErros handleValidationException(MethodArgumentNotValidException ex) {
        return new ApiErros(ex.getBindingResult().getAllErrors().stream().map((erro) -> erro.getDefaultMessage()).collect(Collectors.toList()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleUserNotFound(UserNotFoundException ex) {
        return new ApiErros(ex.getMessage());
    }

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErros handleTokenException(TokenException ex) {
        return new ApiErros(ex.getMessage());
    }

    @ExceptionHandler(PostException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErros handlePostException(PostException ex) {
        return new ApiErros(ex.getMessage());
    }

    @ExceptionHandler(TokenInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErros handleTokenInvalidException(TokenInvalidException ex) {
        return new ApiErros(ex.getMessage());
    }

    @ExceptionHandler(UserApiException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErros handleUserApiException(UserApiException ex) {
        return new ApiErros(ex.getMessage());
    }

}
