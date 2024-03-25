package br.edu.ufcg.virtus.courseautomation.dtos.postsDTO;

import br.edu.ufcg.virtus.courseautomation.models.Poster;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostIdTituloDTO {
    private Long id;
    private String titulo;

    public PostIdTituloDTO(Poster poster) {
        this(poster.getId(), poster.getTitulo());
    }
}
