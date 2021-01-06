package br.edu.ufcg.virtus.courseautomation.dtos;

import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import lombok.Data;

@Data
public class UserWithoutIdDTO {
    private String name;
    private String email;
    private String password;

    public UserWithoutIdDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserWithoutIdDTO(UserApi userApi) {
        this.email = userApi.getEmail();
        this.name = userApi.getName();
        this.password = userApi.getPassword();
    }
}
