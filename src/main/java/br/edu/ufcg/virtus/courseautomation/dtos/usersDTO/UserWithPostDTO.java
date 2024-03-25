package br.edu.ufcg.virtus.courseautomation.dtos.usersDTO;

import br.edu.ufcg.virtus.courseautomation.models.Poster;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class UserWithPostDTO {
    private Long id;
    private String name;
    private String email;
    private List<Poster> posters;

    public UserWithPostDTO(UserApi userApi){
        this(userApi.getId(), userApi.getName(), userApi.getEmail(), userApi.getPosts());
    }

}
