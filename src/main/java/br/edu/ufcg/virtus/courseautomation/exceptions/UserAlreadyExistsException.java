package br.edu.ufcg.virtus.courseautomation.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;
    public UserAlreadyExistsException(String s) {
        super(s);
    }
}
