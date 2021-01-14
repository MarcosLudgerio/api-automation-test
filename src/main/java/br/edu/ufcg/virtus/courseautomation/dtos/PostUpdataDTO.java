package br.edu.ufcg.virtus.courseautomation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Data
@AllArgsConstructor
public class PostUpdataDTO {
    private Optional<String> titulo;

    private Optional<String> texto;
}
