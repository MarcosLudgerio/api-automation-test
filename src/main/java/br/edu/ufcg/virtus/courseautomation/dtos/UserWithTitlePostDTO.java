package br.edu.ufcg.virtus.courseautomation.dtos;

import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserWithTitlePostDTO {
    private Long id;
    private String name;
    private String email;
    private List<String> posts;

    public UserWithTitlePostDTO(UserApi user, List<String> posts) {
        this(user.getId(), user.getName(), user.getEmail(), posts);
    }
}
