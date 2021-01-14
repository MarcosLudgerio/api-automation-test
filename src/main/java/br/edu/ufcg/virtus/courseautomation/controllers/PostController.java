package br.edu.ufcg.virtus.courseautomation.controllers;

import br.edu.ufcg.virtus.courseautomation.dtos.PostCreateDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.PostDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.PostUpdataDTO;
import br.edu.ufcg.virtus.courseautomation.models.Post;
import br.edu.ufcg.virtus.courseautomation.services.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        return new ResponseEntity<>(this.postService.findAllPosts(token), HttpStatus.OK);
    }

    @GetMapping(value = "{id}", produces = "application/json")
    @ApiOperation(value = "Retorna um post específico")
    public ResponseEntity<?> getPost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return new ResponseEntity<>(this.postService.findOneController(token, id), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    @ApiOperation(value = "Cadastra um novo post")
    public ResponseEntity<?> createNewPost(@RequestHeader("Authorization") String token, @RequestBody @Valid PostCreateDTO post) {
        return new ResponseEntity<>(this.postService.createNewPost(token, post), HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}", produces = "application/json")
    @ApiOperation(value = "Atualiza post")
    public ResponseEntity<?> updatePost(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody @Valid PostUpdataDTO post) {
        return new ResponseEntity<>(this.postService.updatePost(token, id, post), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = "application/json")
    @ApiOperation(value = "Apaga post")
    public ResponseEntity<?> dropPost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return new ResponseEntity<>(this.postService.deletePost(token, id), HttpStatus.OK);
    }

}
