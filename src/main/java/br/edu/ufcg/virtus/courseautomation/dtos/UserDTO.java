package br.edu.ufcg.virtus.courseautomation.dtos;


import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String password;

    public UserDTO(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UserDTO(UserApi userApi){
        this(userApi.getId(), userApi.getEmail(), userApi.getPassword());
    }
}
