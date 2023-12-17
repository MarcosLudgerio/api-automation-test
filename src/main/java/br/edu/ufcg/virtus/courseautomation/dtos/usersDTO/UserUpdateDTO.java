package br.edu.ufcg.virtus.courseautomation.dtos.usersDTO;

import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class UserUpdateDTO {

    private Optional<String> name;
    private Optional<String> password;
    private Optional<String> lastname;
    private Optional<String> bio;
    private Optional<String> site;
    private Optional<String> email;
    private Optional<String> urlImage;

}
