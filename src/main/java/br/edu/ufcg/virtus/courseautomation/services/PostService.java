package br.edu.ufcg.virtus.courseautomation.services;


import br.edu.ufcg.virtus.courseautomation.exceptions.PostException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.models.Post;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    public List<Post> findAllPosts(String token) throws UserApiException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi user = userService.validateUsuario(userLog);
        if(user.getName().equals(""))
            throw new UserApiException("Usuário não encontrado, tente novamente!");
        return this.postRepository.findAll();
    }

    public Post findOne(String token, Long id) throws PostException, UserApiException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi user = userService.validateUsuario(userLog);
        if(user.getName().equals(""))
            throw new UserApiException("Usuário não encontrado, tente novamente!");
        Optional<Post> postFind = this.postRepository.findById(id);
        return postFind.orElseThrow(() -> new PostException("Post não encontrado"));
        // return userFind;
    }

    public Post createNewPost(String token, Post post) throws UserApiException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi user = userService.validateUsuario(userLog);
        if(user.getName().equals(""))
            throw new UserApiException("Usuário não encontrado, tente novamente!");
        this.postRepository.save(post);
        return post;
    }

    public Post updatePost(String token, Long id, Post post) throws PostException, UserApiException {

        Post postFinder = this.findOne(token, id);

        if (!post.getAutor().equals(""))
            postFinder.setAutor(post.getAutor());
        if (post.getData() != null)
            postFinder.setData(post.getData());
        if (!post.getTitulo().equals(""))
            postFinder.setTitulo(post.getTitulo());
        if (!post.getTexto().equals(""))
            postFinder.setTexto(post.getTexto());

        this.createNewPost(token, postFinder);
        return postFinder;
    }

    public Post deletePost(String token, Long id) throws PostException, UserApiException {
        System.out.println("token " + token);
        System.out.println("id " + id);
        Post post = this.findOne(token, id);
        this.postRepository.delete(post);
        return post;

    }

}
