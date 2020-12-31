package br.edu.ufcg.virtus.courseautomation.controllers;

import br.edu.ufcg.virtus.courseautomation.exceptions.PostException;
import br.edu.ufcg.virtus.courseautomation.models.Post;
import br.edu.ufcg.virtus.courseautomation.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    public PostService postService;

    @GetMapping(value = "/posts")
    public List<Post> findAll() {
        return this.postService.findAllPosts();
    }

    @RequestMapping(value = "/posts/{id}", produces = "application/json")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.postService.findOne(id), HttpStatus.OK);
        } catch (PostException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/posts")
    public ResponseEntity<Post> createNewPost(@RequestBody Post post) {
        return new ResponseEntity<>(this.postService.createNewPost(post), HttpStatus.CREATED);
    }

    @PutMapping(value = "/posts")
    public ResponseEntity<Post> updatePost(@RequestBody Post post) throws PostException {
        return new ResponseEntity<>(this.postService.updatePost(post), HttpStatus.OK);
    }

    @DeleteMapping(value = "/posts/{id}")
    public ResponseEntity<?> dropPost(@PathVariable Long idPost) {
        try {
            return new ResponseEntity<>(this.postService.deletePost(idPost), HttpStatus.OK);
        } catch (PostException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
