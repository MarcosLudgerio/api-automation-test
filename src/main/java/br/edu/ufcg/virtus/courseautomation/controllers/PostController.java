package br.edu.ufcg.virtus.courseautomation.controllers;

import br.edu.ufcg.virtus.courseautomation.exceptions.HandleException;
import br.edu.ufcg.virtus.courseautomation.exceptions.PostException;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.models.Post;
import br.edu.ufcg.virtus.courseautomation.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    @Autowired
    public PostService postService;

    @GetMapping(value = "/posts", produces = "application/json")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String token) {
        try {
            return new ResponseEntity<>(this.postService.findAllPosts(token), HttpStatus.OK);
        } catch (UserApiException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (TokenException e) {
            return new ResponseEntity<>(HandleException.invalidToken(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/posts/{id}", produces = "application/json")
    public ResponseEntity<?> getPost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.postService.findOne(token, id), HttpStatus.OK);
        } catch (PostException | UserApiException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        } catch (TokenException e) {
            return new ResponseEntity<>(HandleException.invalidToken(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/posts", produces = "application/json")
    public ResponseEntity<?> createNewPost(@RequestHeader("Authorization") String token, @RequestBody Post post) {
        try {
            return new ResponseEntity<>(this.postService.createNewPost(token, post), HttpStatus.CREATED);
        } catch (UserApiException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (TokenException e) {
            return new ResponseEntity<>(HandleException.invalidToken(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/posts/{id}", produces = "application/json")
    public ResponseEntity<?> updatePost(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody Post post) throws PostException {
        try {
            return new ResponseEntity<>(this.postService.updatePost(token, id, post), HttpStatus.OK);
        } catch (UserApiException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (TokenException e) {
            return new ResponseEntity<>(HandleException.invalidToken(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/posts/{id}", produces = "application/json")
    public ResponseEntity<?> dropPost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.postService.deletePost(token, id), HttpStatus.OK);
        } catch (PostException | UserApiException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (TokenException e) {
            return new ResponseEntity<>(HandleException.invalidToken(e, "/users").getBody(), HttpStatus.UNAUTHORIZED);
        }
    }

}
