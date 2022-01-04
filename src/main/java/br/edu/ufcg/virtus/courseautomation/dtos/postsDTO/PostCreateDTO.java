package br.edu.ufcg.virtus.courseautomation.dtos.postsDTO;

import br.edu.ufcg.virtus.courseautomation.models.Post;
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

    public PostCreateDTO(Post post) {
      this(post.getTitulo(), post.getTexto());
    }

}
