package br.edu.ufcg.virtus.courseautomation.dtos;

import br.edu.ufcg.virtus.courseautomation.models.Post;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PostDTO {

    private String titulo;

    private UserWithoutPassDTO autor;

    private LocalDate data;

    private String texto;

    public PostDTO(Post post) {
        this.titulo = post.getTitulo();
        this.autor = new UserWithoutPassDTO(post.getAutor());
        this.data = post.getData();
        this.texto = post.getTexto();
    }
}
