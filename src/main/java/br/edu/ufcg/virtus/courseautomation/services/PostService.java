package br.edu.ufcg.virtus.courseautomation.services;


import br.edu.ufcg.virtus.courseautomation.dtos.postsDTO.*;
import br.edu.ufcg.virtus.courseautomation.exceptions.PostException;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.models.Poster;
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
        List<Poster> posters = this.postRepository.findAll();
        return posters.stream().map(PostIdTituloDTO::new).collect(Collectors.toList());
    }

    public List<String> findPostByCreator(UserApi user) {
        return this.postRepository.findPostsByAutor(user.getId());
    }

    private Poster findOne(String token, Long id) throws PostException, UserApiException, TokenException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi user = userService.validateUser(userLog);
        if (user.getName().equals("")) {
            throw new UserApiException("Dados inválidos");
        }
        Optional<Poster> postFind = this.postRepository.findById(id);
        if (!postFind.isPresent())
            throw new PostException("Post não encontrado, tente novamente");
        postFind.get().setAutor(user);
        return postFind.get();
    }

    public PostTituloDataTextoDTO findPost(Long id) {
        Optional<Poster> postFind = this.postRepository.findById(id);
        if (!postFind.isPresent())
            throw new PostException("Post não encontrado, tente novamente");
        return new PostTituloDataTextoDTO(postFind.get());
    }

    public PostDTO findOneController(String token, Long id) throws PostException, UserApiException, TokenException {
        Poster poster = this.findOne(token, id);
        return new PostDTO(poster);
    }

    public PostDTO createNewPost(String token, PostCreateDTO post) throws UserApiException, TokenException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi user = userService.validateUser(userLog);
        if (user.getName().equals(""))
            throw new UserApiException("Dados inválidos");
        Poster posterReturn = this.fromDTO(post);
        posterReturn.setAutor(user);
        this.postRepository.save(posterReturn);
        return new PostDTO(posterReturn);
    }

    public PostDTO updatePost(String token, Long id, PostUpdateDTO post) throws PostException, UserApiException, TokenException {
        Poster posterFinder = this.findOne(token, id);
        if (post.getTitulo().isPresent() && !post.getTitulo().get().equals(""))
            posterFinder.setTitulo(post.getTitulo().get());
        if (post.getTexto().isPresent() && !post.getTexto().get().equals(""))
            posterFinder.setTexto(post.getTexto().get());
        this.postRepository.save(posterFinder);
        return new PostDTO(posterFinder);
    }

    public PostDTO deletePost(String token, Long id) throws PostException, UserApiException, TokenException {
        Poster poster = this.findOne(token, id);
        this.postRepository.delete(poster);
        return new PostDTO(poster);
    }

    public Poster fromDTO(PostDTO postDTO) {
        return new Poster(null, postDTO.getTitulo(), userService.fromDTO(postDTO.getAutor()), postDTO.getData(), postDTO.getTexto());
    }

    public Poster fromDTO(PostCreateDTO postDTO) {
        return new Poster(null, postDTO.getTitulo(), null, LocalDate.now(), postDTO.getTexto());
    }
}
