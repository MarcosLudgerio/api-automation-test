package br.edu.ufcg.virtus.courseautomation.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {

    }

    public UserNotFoundException(String s) {
        super(s);
    }
}
