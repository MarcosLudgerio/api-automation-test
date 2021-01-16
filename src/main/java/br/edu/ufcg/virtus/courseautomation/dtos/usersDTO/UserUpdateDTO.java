package br.edu.ufcg.virtus.courseautomation.dtos.usersDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class UserUpdateDTO {
    private Optional<String> name;

    private Optional<String> email;
}
