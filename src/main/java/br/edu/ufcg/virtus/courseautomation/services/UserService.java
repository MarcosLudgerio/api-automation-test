package br.edu.ufcg.virtus.courseautomation.services;

import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.*;
import br.edu.ufcg.virtus.courseautomation.exceptions.*;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

    public List<UserApi> findAllUsersApi() {
        return this.userRepository.findAll();
    }

    public UserDetailsDTO findOne(String token) throws UserApiException, TokenException {
        if (token == null || token.equals(""))
            throw new TokenInvalidException("Erro de validação do token");
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi userFinder = this.validateUser(userLog);
        List<String> posts = this.postService.findPostByCreator(userFinder);
        return new UserDetailsDTO(userFinder, posts);
    }

    public UserApi findOne(Long id) throws UserApiException, TokenException {
        if (!this.userRepository.findById(id).isPresent()) throw new UserApiException("Usuário não encontrado!");
        return (UserApi) this.userRepository.findById(id).get();
    }

    public UserApi findByEmail(String email) throws UserApiException {
        Optional<UserApi> userFind = this.userRepository.findByEmail(email);
        return userFind.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado, verifique os dados e tente novamente"));
    }

    public UserDetailsDTO createNewUser(UserApi user) throws UserAlreadyExistsException {
        Optional<UserApi> userFind = this.userRepository.findByEmail(user.getEmail());
        if (userFind.isPresent()) throw new UserAlreadyExistsException("Usuário com este email ja foi cadastrado");

        this.userRepository.save(user);
        return new UserDetailsDTO(user, new ArrayList<>());
    }

    public UserApi fromDTO(UserDTO userDTO) {
        UserApi userApi = new UserApi(userDTO.getId(), userDTO.getName(), userDTO.getLastname(), userDTO.getEmail(), userDTO.getPassword(), "Sem bio", userDTO.getSite(), null);
        userDTO.getBio().ifPresent((obj) -> userApi.setBio(userDTO.getBio().get()));
        userDTO.getUrlImage().ifPresent((obj) -> userApi.setUrlImageProfile(userDTO.getUrlImage().get()));
        return userApi;
    }


    public UserApi fromDTO(UserWithoutPassDTO userDTO) {
        return new UserApi(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), null, null, null, null, null);
    }


    public UserDetailsDTO updateUser(String token, UserUpdateDTO userUpdateDTO) throws UserAlreadyExistsException, UserApiException, TokenException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi userFinder = this.validateUser(userLog);
        if (userUpdateDTO.getName().isPresent())
            userFinder.setName(userUpdateDTO.getName().get());
        if (userUpdateDTO.getPassword().isPresent())
            userFinder.setPassword(userUpdateDTO.getPassword().get());
        if (userUpdateDTO.getBio().isPresent())
            userFinder.setBio(userUpdateDTO.getBio().get());
        if (userUpdateDTO.getLastname().isPresent())
            userFinder.setLastname(userUpdateDTO.getLastname().get());
        if (userUpdateDTO.getSite().isPresent())
            userFinder.setSite(userUpdateDTO.getSite().get());
        if (userUpdateDTO.getUrlImage().isPresent())
            userFinder.setUrlImageProfile(userUpdateDTO.getUrlImage().get());
        this.userRepository.save(userFinder);
        return new UserDetailsDTO(userFinder, new ArrayList<>());
    }

    public UserApi validateUser(Optional<String> id) throws UserApiException {
        if (id == null || !id.isPresent())
            throw new UserNotFoundException("Usuário não encontrado, verifique os dados e tente novamente ");
        Optional<UserApi> user = this.userRepository.findByEmail(id.get());
        if (!user.isPresent())
            throw new UserApiException("Dados invalidos");
        return user.get();
    }

}
