package br.edu.ufcg.virtus.courseautomation.dtos.usersDTO;

import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserWithoutIdDTO;
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

    public UserWithoutPassDTO(UserWithoutIdDTO userApi) {
        this.email = userApi.getEmail();
        this.name = userApi.getName();

    }
}
