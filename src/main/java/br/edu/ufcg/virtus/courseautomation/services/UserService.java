package br.edu.ufcg.virtus.courseautomation.services;

import br.edu.ufcg.virtus.courseautomation.dtos.UserDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.UserWithPostDTO;
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
    JWTService jwtService;

    public List<UserWithoutPassDTO> findAllUsers() {
        List<UserApi> users = this.userRepository.findAll();
        List<UserWithoutPassDTO> usersDTO = new ArrayList<>();
        for (UserApi u : users) usersDTO.add(new UserWithoutPassDTO(u));
        return usersDTO;
    }

    public UserWithPostDTO findOne(String token) throws UserApiException, TokenException {
        if (token.equals(null) || token.equals(""))
            throw new TokenInvalidException("Validation token error.");
        Optional<String> userLog = jwtService.restoreAccount(token);
        UserApi userFinder = this.validateUsuario(userLog);
        return new UserWithPostDTO(userFinder);
    }

    public UserApi findByEmail(String email) throws UserApiException {
        Optional<UserApi> userFind = this.userRepository.findByEmail(email);
        return userFind.orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public UserWithoutPassDTO createNewUser(UserApi user) throws UserAlreadyExistsException {
        Optional<UserApi> userFind = this.userRepository.findByEmail(user.getEmail());
        if (userFind.isPresent()) throw new UserAlreadyExistsException("User already exists, verify e-mail and try again");

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
            throw new UserNotFoundException("User not found, try again");
        Optional<UserApi> user = this.userRepository.findByEmail(id.get());
        if (!user.isPresent())
            throw new UserApiException("Invalid data, please try again");
        return user.get();
    }
}
