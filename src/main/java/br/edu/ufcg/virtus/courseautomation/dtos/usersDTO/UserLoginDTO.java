package br.edu.ufcg.virtus.courseautomation.dtos.usersDTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UserLoginDTO {
    @NotEmpty(message = "Campo email é obrigatório")
    @Email(message = "O email precisa ser válido")
    private String email;

    @NotBlank(message = "Campo senha é obrigatório")
    private String password;
}
