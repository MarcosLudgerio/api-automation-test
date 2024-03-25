package br.edu.ufcg.virtus.courseautomation.dtos.postsDTO;

import br.edu.ufcg.virtus.courseautomation.models.Poster;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class PostCreateDTO {
    @NotBlank(message = "Campo título do post é obrigatório")
    private String titulo;

    @NotBlank(message = "Campo texto é obrigatório")
    private String texto;

    public PostCreateDTO(Poster poster) {
      this(poster.getTitulo(), poster.getTexto());
    }

}
