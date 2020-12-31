package br.edu.ufcg.virtus.courseautomation.services;


import br.edu.ufcg.virtus.courseautomation.exceptions.PostException;
import br.edu.ufcg.virtus.courseautomation.models.Post;
import br.edu.ufcg.virtus.courseautomation.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAllPosts() {
        return this.postRepository.findAll();
    }

    public Post findOne(Long id) throws PostException {
        Optional<Post> postFind = this.postRepository.findById(id);
        return postFind.orElseThrow(() -> new PostException("Post não encontrado"));
        // return userFind;
    }

    public Post createNewPost(Post post) {
        this.postRepository.save(post);
        return post;
    }

    public Post updatePost(Post post) throws PostException {
        Post postFinder = this.findOne(post.getId());
        postFinder.setAutor(post.getAutor());
        postFinder.setData(post.getData());
        postFinder.setTitulo(post.getTitulo());
        postFinder.setTexto(post.getTexto());
        this.createNewPost(postFinder);
        return postFinder;
    }

    public Post deletePost(Long id) throws PostException {
        Post post = this.findOne(id);
        this.postRepository.delete(post);
        return post;

    }

}
