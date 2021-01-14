package br.edu.ufcg.virtus.courseautomation.dtos;

import br.edu.ufcg.virtus.courseautomation.models.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Getter
@Setter
public class PostTituloDataTextoDTO {
    @NotBlank(message = "Título do post é obrigatório")
    private String titulo;

    @NotBlank(message = "data do post é obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate data;

    @NotBlank(message = "Título do post é obrigatório")
    private String texto;

    public PostTituloDataTextoDTO(Post post) {
        this(post.getTitulo(), post.getData(), post.getTexto());
    }
    public PostTituloDataTextoDTO(String titulo, LocalDate data, String texto){
        this.texto = texto;
        this.titulo = titulo;
        this.data = data;
    }
}
