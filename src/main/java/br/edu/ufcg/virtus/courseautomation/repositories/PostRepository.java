package br.edu.ufcg.virtus.courseautomation.repositories;

import br.edu.ufcg.virtus.courseautomation.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface PostRepository <T, ID extends Serializable> extends JpaRepository<Post, Long> {
}
