package br.edu.ufcg.virtus.courseautomation.services;


import br.edu.ufcg.virtus.courseautomation.dtos.PostDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.PostTituloDataTextoDTO;
import br.edu.ufcg.virtus.courseautomation.exceptions.PostException;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.models.Post;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    public List<PostTituloDataTextoDTO> findAllPosts(String token) throws UserApiException, TokenException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi user = userService.validateUsuario(userLog);
        if (user.getName().equals(""))
            throw new UserApiException("Invalidate data, please try again");
        List<Post> posts = this.postRepository.findAll();
        return posts.stream().map((post) -> new PostTituloDataTextoDTO(post)).collect(Collectors.toList());

    }

    public Post findOne(String token, Long id) throws PostException, UserApiException, TokenException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi user = userService.validateUsuario(userLog);
        if (user.getName().equals(""))
            throw new UserApiException("Invalidate data, please try again");
        Optional<Post> postFind = this.postRepository.findById(id);
        return postFind.orElseThrow(() -> new PostException());
    }

    public PostDTO createNewPost(String token, Post post) throws UserApiException, TokenException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi user = userService.validateUsuario(userLog);
        if (user.getName().equals(""))
            throw new UserApiException("Invalidate data, please try again");
        this.postRepository.save(post);
        return new PostDTO(post);
    }

    public Post updatePost(String token, Long id, Post post) throws PostException, UserApiException, TokenException {

        Post postFinder = this.findOne(token, id);
        if (!post.getAutor().equals(null))
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

    public Post deletePost(String token, Long id) throws PostException, UserApiException, TokenException {
        Post post = this.findOne(token, id);
        this.postRepository.delete(post);
        return post;
    }

    public Post fromDTO(PostDTO postDTO) {
        //Long id, String titulo, UserApi autor, LocalDate data, String texto
        return new Post(null, postDTO.getTitulo(), userService.fromDTO(postDTO.getAutor()), postDTO.getData(), postDTO.getTexto());
    }


}
