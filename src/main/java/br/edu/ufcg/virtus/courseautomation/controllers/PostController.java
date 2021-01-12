package br.edu.ufcg.virtus.courseautomation.controllers;

import br.edu.ufcg.virtus.courseautomation.controllers.exceptions.ExceptionHandle;
import br.edu.ufcg.virtus.courseautomation.exceptions.PostException;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.models.Post;
import br.edu.ufcg.virtus.courseautomation.services.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/posts", produces = "application/json")
@Api(value = "API Rest Curso Automação")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    public PostService postService;

    @GetMapping(value = "", produces = "application/json")
    @ApiOperation(value = "Retorna todos os posts cadastrados")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(this.postService.findAllPosts(token), HttpStatus.OK);
        } catch (TokenException ex) {
            return new ResponseEntity<>(ExceptionHandle.invalidToken(ex, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        } catch (UserApiException exception) {
            return new ResponseEntity<>(ExceptionHandle.noPrivilegesForThat(exception, "/users").getBody(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "{id}", produces = "application/json")
    @ApiOperation(value = "Retorna um post específico")
    public ResponseEntity<?> getPost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.postService.findOne(token, id), HttpStatus.OK);
        } catch (TokenException exception) {
            return new ResponseEntity<>(ExceptionHandle.invalidToken(exception, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        } catch (UserApiException exception) {
            return new ResponseEntity<>(ExceptionHandle.noPrivilegesForThat(exception, "/users").getBody(), HttpStatus.NOT_FOUND);
        } catch (PostException e) {
            return new ResponseEntity<>(ExceptionHandle.postNotFound(e, "/users").getBody(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "", produces = "application/json")
    @ApiOperation(value = "Cadastra um novo post")
    public ResponseEntity<?> createNewPost(@RequestHeader("Authorization") String token, @RequestBody Post post) {
        try {
            return new ResponseEntity<>(this.postService.createNewPost(token, post), HttpStatus.CREATED);
        } catch (UserApiException exception) {
            return new ResponseEntity<>(ExceptionHandle.noPrivilegesForThat(exception, "/users").getBody(), HttpStatus.NOT_FOUND);
        } catch (TokenException e) {
            return new ResponseEntity<>(ExceptionHandle.invalidToken(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "{id}", produces = "application/json")
    @ApiOperation(value = "Atualiza post")
    public ResponseEntity<?> updatePost(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody Post post) {
        try {
            return new ResponseEntity<>(this.postService.updatePost(token, id, post), HttpStatus.OK);
        } catch (UserApiException exception) {
            return new ResponseEntity<>(ExceptionHandle.noPrivilegesForThat(exception, "/users").getBody(), HttpStatus.NOT_FOUND);
        } catch (TokenException e) {
            return new ResponseEntity<>(ExceptionHandle.invalidToken(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        } catch (PostException e) {
            return new ResponseEntity<>(ExceptionHandle.postNotFound(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        }

    }

    @DeleteMapping(value = "{id}", produces = "application/json")
    @ApiOperation(value = "Apaga post")
    public ResponseEntity<?> dropPost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.postService.deletePost(token, id), HttpStatus.OK);
        } catch (PostException e) {
            return new ResponseEntity<>(ExceptionHandle.postNotFound(e, "/users").getBody(), HttpStatus.NOT_FOUND);
        } catch (UserApiException exception) {
            return new ResponseEntity<>(ExceptionHandle.noPrivilegesForThat(exception, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        } catch (TokenException e) {
            return new ResponseEntity<>(ExceptionHandle.invalidToken(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        }
    }

}
