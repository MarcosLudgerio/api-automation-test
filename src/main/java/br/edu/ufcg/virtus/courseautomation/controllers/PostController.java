package br.edu.ufcg.virtus.courseautomation.controllers;

import br.edu.ufcg.virtus.courseautomation.dtos.postsDTO.PostCreateDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.postsDTO.PostUpdateDTO;
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
@RequestMapping(value = "/api/posters", produces = "application/json")
@Api(value = "API Rest Curso Automação")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    public PostService postService;

    @GetMapping(value = "", produces = "application/json")
    @ApiOperation(value = "Retorna todos os posts cadastrados")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(this.postService.findAllPosts(), HttpStatus.OK);
    }

    @GetMapping(value = "{id}", produces = "application/json")
    @ApiOperation(value = "Retorna que o usuário cadastrou")
    public ResponseEntity<?> getPost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return new ResponseEntity<>(this.postService.findOneController(token, id), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/details", produces = "application/json")
    @ApiOperation(value = "Retorna detalhes de um post específico")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return new ResponseEntity<>(this.postService.findPost(id), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    @ApiOperation(value = "Cadastra um novo post")
    public ResponseEntity<?> createNewPost(@RequestHeader("Authorization") String token, @RequestBody @Valid PostCreateDTO post) {
        return new ResponseEntity<>(this.postService.createNewPost(token, post), HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}", produces = "application/json")
    @ApiOperation(value = "Atualiza post")
    public ResponseEntity<?> updatePost(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody @Valid PostUpdateDTO post) {
        return new ResponseEntity<>(this.postService.updatePost(token, id, post), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = "application/json")
    @ApiOperation(value = "Apaga post")
    public ResponseEntity<?> dropPost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return new ResponseEntity<>(this.postService.deletePost(token, id), HttpStatus.OK);
    }

}
