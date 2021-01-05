package br.edu.ufcg.virtus.courseautomation.dtos;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String email;
    private String password;
}
