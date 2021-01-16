package br.edu.ufcg.virtus.courseautomation.dtos.postsDTO;

import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserWithoutPassDTO;
import br.edu.ufcg.virtus.courseautomation.models.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PostDTO {

    @NotBlank(message = "Título do post é obrigatório")
    private String titulo;

    @NotNull(message = "Autor é obrigatório")
    private UserWithoutPassDTO autor;

    @NotBlank(message = "data do post é obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate data;

    @NotBlank(message = "Título do post é obrigatório")
    private String texto;

    public PostDTO(Post post) {
        this.titulo = post.getTitulo();
        this.autor = new UserWithoutPassDTO(post.getAutor());
        this.data = post.getData();
        this.texto = post.getTexto();
    }
}
