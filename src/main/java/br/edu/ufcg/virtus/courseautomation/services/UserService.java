package br.edu.ufcg.virtus.courseautomation.services;

import br.edu.ufcg.virtus.courseautomation.dtos.UserDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.UserWithTitlePostDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.UserWithoutPassDTO;
import br.edu.ufcg.virtus.courseautomation.exceptions.*;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;

    @Autowired
    JWTService jwtService;

    public List<UserWithoutPassDTO> findAllUsers() {
        List<UserApi> users = this.userRepository.findAll();
        List<UserWithoutPassDTO> usersDTO = new ArrayList<>();
        for (UserApi u : users) usersDTO.add(new UserWithoutPassDTO(u));
        return usersDTO;
    }

    public UserWithTitlePostDTO findOne(String token) throws UserApiException, TokenException {
        if (token.equals(null) || token.equals(""))
            throw new TokenInvalidException("Erro de validação do token");
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi userFinder = this.validateUsuario(userLog);
        List<String> posts = this.postService.findPostByCreator(userFinder);
        UserWithTitlePostDTO userReturn = new UserWithTitlePostDTO(userFinder, posts);
        return userReturn;
    }

    public UserApi findByEmail(String email) throws UserApiException {
        Optional<UserApi> userFind = this.userRepository.findByEmail(email);
        return userFind.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado, verifique os dados e tente novamente"));
    }

    public UserWithoutPassDTO createNewUser(UserApi user) throws UserAlreadyExistsException {
        Optional<UserApi> userFind = this.userRepository.findByEmail(user.getEmail());
        if (userFind.isPresent()) throw new UserAlreadyExistsException("Usuário com este email ja foi cadastrado");

        this.userRepository.save(user);
        return new UserWithoutPassDTO(user);
    }

    public UserApi fromDTO(UserDTO userDTO){
        return new UserApi(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(), null);
    }

    public UserApi fromDTO(UserWithoutPassDTO userDTO){
        return new UserApi(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), null, null);
    }

    public UserWithoutPassDTO updateUser(String token, UserApi user) throws UserAlreadyExistsException, UserApiException, TokenException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi userFinder = this.validateUsuario(userLog);
        if (!user.getName().equals(""))
            userFinder.setName(user.getName());
        if (!user.getPassword().equals(""))
            userFinder.setPassword(user.getPassword());
        if (!user.getEmail().equals(""))
            userFinder.setEmail(user.getEmail());
        this.createNewUser(userFinder);
        return new UserWithoutPassDTO(userFinder);
    }

    public UserApi validateUsuario(Optional<String> id) throws UserApiException {
        if (!id.isPresent())
            throw new UserNotFoundException("Usuário não encontrado, verifique os dados e tente novamente ");
        Optional<UserApi> user = this.userRepository.findByEmail(id.get());
        if (!user.isPresent())
            throw new UserApiException("Dados invalidos");
        return user.get();
    }
}
