package br.edu.ufcg.virtus.courseautomation.dtos;


import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDTO {
    private Long id;
    @NotBlank(message = "Campo nome é obrigatório")
    private String name;
    @NotBlank(message = "Campo email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    @NotBlank(message = "Campo senha é obrigatório")
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
