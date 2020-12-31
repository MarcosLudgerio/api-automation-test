package br.edu.ufcg.virtus.courseautomation.dtos;

import br.edu.ufcg.virtus.courseautomation.models.Post;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.services.PostService;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PostDTO {

    private String titulo;

    private UserApi autor;

    private LocalDate data;

    private String texto;

    public PostDTO(String titulo, UserApi autor, LocalDate data, String texto) {
        this.titulo = titulo;
        this.autor = autor;
        this.data = data;
        this.texto = texto;
    }

    public PostDTO(Post post){
        this(post.getTitulo(), post.getAutor(), post.getData(), post.getTexto());
    }
}
