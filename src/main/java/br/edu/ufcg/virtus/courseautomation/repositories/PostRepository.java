package br.edu.ufcg.virtus.courseautomation.repositories;

import br.edu.ufcg.virtus.courseautomation.models.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface PostRepository <T, ID extends Serializable> extends JpaRepository<Poster, Long> {
    @Query(value = "SELECT p.titulo FROM post p WHERE p.autor.id = ?1")
    List<String> findPostsByAutor(Long id);
}
