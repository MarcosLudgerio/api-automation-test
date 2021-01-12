package br.edu.ufcg.virtus.courseautomation.services;

import br.edu.ufcg.virtus.courseautomation.dtos.UserDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.UserWithPostDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.UserWithoutPassDTO;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserAlreadyExistsException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
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
    JWTService jwtService;

    public List<UserWithoutPassDTO> findAllUsers() {
        List<UserApi> users = this.userRepository.findAll();
        List<UserWithoutPassDTO> usersDTO = new ArrayList<>();
        for (UserApi u : users) usersDTO.add(new UserWithoutPassDTO(u));
        return usersDTO;
    }

    public UserWithPostDTO findOne(String token) throws UserApiException, TokenException {
        if (token.equals(null) || token.equals(""))
            throw new TokenException("Token inválido, verifique os dados e tente novamente");
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi userFinder = this.validateUsuario(userLog);
        return new UserWithPostDTO(userFinder);
    }

    public UserApi findByEmail(String email) throws UserApiException {
        Optional<UserApi> userFind = this.userRepository.findByEmail(email);
        return userFind.orElseThrow(() -> new UserApiException("Usuário não encontrado"));
    }

    public UserWithoutPassDTO createNewUser(UserApi user) throws UserAlreadyExistsException {
        Optional<UserApi> userFind = this.userRepository.findByEmail(user.getEmail());
        if (userFind.isPresent()) throw new UserAlreadyExistsException("Usuário já cadastrado no sistema");

        this.userRepository.save(user);
        return new UserWithoutPassDTO(user);
    }

    public UserApi fromDTO(UserDTO userDTO){
        return new UserApi(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(), null);
    }

    public UserApi updateUser(String token, UserApi user) throws UserAlreadyExistsException, UserApiException, TokenException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi userFinder = this.validateUsuario(userLog);
        if (!user.getName().equals(""))
            userFinder.setName(user.getName());
        if (!user.getPassword().equals(""))
            userFinder.setPassword(user.getPassword());
        if (!user.getEmail().equals(""))
            userFinder.setEmail(user.getEmail());
        this.createNewUser(userFinder);
        return userFinder;
    }

    public UserApi validateUsuario(Optional<String> id) throws UserApiException {
        if (!id.isPresent())
            throw new UserApiException("Usuário não encontrado, tente novamente");
        Optional<UserApi> user = this.userRepository.findByEmail(id.get());
        if (!user.isPresent())
            throw new UserApiException("Dados inválidos, tente novamente");
        return user.get();
    }
}
