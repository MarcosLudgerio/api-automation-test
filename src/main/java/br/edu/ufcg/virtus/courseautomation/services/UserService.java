package br.edu.ufcg.virtus.courseautomation.services;

import br.edu.ufcg.virtus.courseautomation.dtos.UserDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.UserWithoutPassDTO;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenException;
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
        for(UserApi u : users) usersDTO.add(new UserWithoutPassDTO(u));
        return usersDTO;
    }

    public UserApi findOne(String token) throws UserApiException, TokenException {
        if (token.equals(null) || token.equals(""))
            throw new UserApiException("Erro de validação, tente novamente");

        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi userFinder = this.validateUsuario(userLog);
        return userFinder;
    }

    public UserApi findOneAuth(Long id) throws UserApiException {
        Optional<UserApi> userFind = this.userRepository.findById(id);
        return userFind.orElseThrow(() -> new UserApiException("Usuário não encontrado, tente novamente"));
    }


    public UserDTO createNewUser(UserApi user) {
        this.userRepository.save(user);
        return new UserDTO(user);
    }

    public UserApi updateUser(String token, UserApi user) throws UserApiException, TokenException {
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

    public UserDTO deleteUser(String token) throws UserApiException, TokenException {
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi user = this.validateUsuario(userLog);
        this.userRepository.delete(user);
        return new UserDTO(user);
    }

    public UserApi validateUsuario(Optional<String> id) throws UserApiException {
        if (!id.isPresent())
            throw new UserApiException("Usuário não encontrado");
        Optional<UserApi> usuario = this.userRepository.findByEmail(id.get());
        if (!usuario.isPresent())
            throw new UserApiException("E-mail não encontrado!");
        return usuario.get();
    }
}
