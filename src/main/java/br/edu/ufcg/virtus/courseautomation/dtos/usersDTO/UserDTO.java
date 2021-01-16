package br.edu.ufcg.virtus.courseautomation.dtos.usersDTO;


import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {

    private Long id;

    @NotEmpty(message = "Campo nome é obrigatório")
    private String name;

    @Email(message = "Email inválido")
    @NotBlank(message = "Campo email é obrigatório")
    private String email;

    @NotEmpty(message = "Campo senha é obrigatório")
    @Length(min = 8, max = 64, message = "A senha deve ter entre 8 e 64 caracteres")
    private String password;

    public UserDTO(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public UserDTO(UserApi userApi) {
        this(userApi.getId(), userApi.getName(), userApi.getEmail(), userApi.getPassword());
    }
}
