package br.edu.ufcg.virtus.courseautomation.exceptions;

import lombok.Data;


public class UserApiException extends Exception {
    public UserApiException(String message) {
        super(message);
    }
}
