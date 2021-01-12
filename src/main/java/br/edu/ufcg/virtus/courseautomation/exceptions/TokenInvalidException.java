package br.edu.ufcg.virtus.courseautomation.exceptions;

public class TokenInvalidException extends RuntimeException{

    public TokenInvalidException() {
    }

    public TokenInvalidException(String s) {
        super(s);
    }
}
