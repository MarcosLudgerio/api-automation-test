package br.edu.ufcg.virtus.courseautomation.dtos.usersDTO;


import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty(message = "Campo nome é obrigatório")
    private String name;

    @NotEmpty(message = "Campo sobreonme é obrigatório")
    private String lastname;

    @Email(message = "Email inválido")
    @NotBlank(message = "Campo email é obrigatório")
    private String email;

    @NotEmpty(message = "Campo senha é obrigatório")
    @Length(min = 8, max = 64, message = "A senha deve ter entre 8 e 64 caracteres")
    private String password;

    private Optional<String> bio;

    private Optional<String> site;

    private Optional<String> urlImage;



}
