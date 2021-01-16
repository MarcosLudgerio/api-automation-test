package br.edu.ufcg.virtus.courseautomation.dtos.postsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class PostUpdateDTO {
    private Optional<String> titulo;

    private Optional<String> texto;
}
