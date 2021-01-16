package br.edu.ufcg.virtus.courseautomation.dtos.usersDTO;

import br.edu.ufcg.virtus.courseautomation.dtos.postsDTO.PostIdTituloDTO;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserPostDTO {
    private String name;
    private String email;
    private List<PostIdTituloDTO> posts;

    public UserPostDTO(UserApi user) {
        this(user.getName(), user.getEmail());
    }

    public UserPostDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
