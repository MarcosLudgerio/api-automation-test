package br.edu.ufcg.virtus.courseautomation.exceptions;

public class UserApiException extends RuntimeException {
    public UserApiException(String message) {
        super(message);
    }

    public UserApiException() {
    }
}
