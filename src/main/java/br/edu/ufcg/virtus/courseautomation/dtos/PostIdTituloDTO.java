package br.edu.ufcg.virtus.courseautomation.dtos;

import br.edu.ufcg.virtus.courseautomation.models.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostIdTituloDTO {
    private Long id;
    private String titulo;

    public PostIdTituloDTO(Post post) {
        this(post.getId(), post.getTitulo());
    }
}
