package br.edu.ufcg.virtus.courseautomation.dtos;

import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import lombok.Data;

@Data
public class UserWithoutPassDTO {
    private Long id;
    private String email;
    private String name;

    public UserWithoutPassDTO(UserApi userApi) {
        this.id = userApi.getId();
        this.email = userApi.getEmail();
        this.name = userApi.getName();
    }
}
