package br.edu.ufcg.virtus.courseautomation.services;


import br.edu.ufcg.virtus.courseautomation.dtos.postsDTO.*;
import br.edu.ufcg.virtus.courseautomation.exceptions.PostException;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.models.Post;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<PostIdTituloDTO> findAllPosts() throws UserApiException, TokenException {
        List<Post> posts = this.postRepository.findAll();
        return posts.stream().map((post) -> new PostIdTituloDTO(post)).collect(Collectors.toList());
    }

    public List<String> findPostByCreator(UserApi user) {
        return this.postRepository.findPostsByAutor(user.getId());
    }

    private Post findOne(String token, Long id) throws PostException, UserApiException, TokenException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi user = userService.validateUsuario(userLog);
        if (user.getName().equals("")) {
            throw new UserApiException("Dados inválidos");
        }
        Optional<Post> postFind = this.postRepository.findById(id);
        if (!postFind.isPresent())
            throw new PostException("Post não encontrado, tente novamente");
        postFind.get().setAutor(user);
        return postFind.get();
    }

    public PostTituloDataTextoDTO findPost(Long id) {
        Optional<Post> postFind = this.postRepository.findById(id);
        if (!postFind.isPresent())
            throw new PostException("Post não encontrado, tente novamente");
        return new PostTituloDataTextoDTO(postFind.get());
    }

    public PostDTO findOneController(String token, Long id) throws PostException, UserApiException, TokenException {
        Post post = this.findOne(token, id);
        return new PostDTO(post);
    }

    public PostDTO createNewPost(String token, PostCreateDTO post) throws UserApiException, TokenException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi user = userService.validateUsuario(userLog);
        if (user.getName().equals(""))
            throw new UserApiException("Dados inválidos");
        Post postReturn = this.fromDTO(post);
        postReturn.setAutor(user);
        this.postRepository.save(postReturn);
        return new PostDTO(postReturn);
    }

    public PostDTO updatePost(String token, Long id, PostUpdateDTO post) throws PostException, UserApiException, TokenException {
        Post postFinder = this.findOne(token, id);
        if (post.getTitulo().isPresent() && !post.getTitulo().get().equals(""))
            postFinder.setTitulo(post.getTitulo().get());
        if (post.getTexto().isPresent() && !post.getTexto().get().equals(""))
            postFinder.setTexto(post.getTexto().get());
        this.postRepository.save(postFinder);
        return new PostDTO(postFinder);
    }

    public PostDTO deletePost(String token, Long id) throws PostException, UserApiException, TokenException {
        Post post = this.findOne(token, id);
        this.postRepository.delete(post);
        return new PostDTO(post);
    }

    public Post fromDTO(PostDTO postDTO) {
        return new Post(null, postDTO.getTitulo(), userService.fromDTO(postDTO.getAutor()), postDTO.getData(), postDTO.getTexto());
    }

    public Post fromDTO(PostCreateDTO postDTO) {
        return new Post(null, postDTO.getTitulo(), null, LocalDate.now(), postDTO.getTexto());
    }
}
