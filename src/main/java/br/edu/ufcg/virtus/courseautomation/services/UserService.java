package br.edu.ufcg.virtus.courseautomation.services;

import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserApi> findAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<UserApi> findOne(Long id) throws UserApiException {
        Optional<UserApi> userFind = this.userRepository.findById(id);
       // return userFind.orElseThrow(() -> new UserApiException("Usuário não encontrado, tente novamente"));
    return userFind;
    }

    public UserApi createNewUser(UserApi user) {
        this.userRepository.save(user);
        return user;
    }

    public UserApi updateUser(UserApi user) throws UserApiException {
        UserApi userFinder = this.findOne(user.getId()).get();
        userFinder.setName(user.getName());
        userFinder.setPassword(user.getPassword());
        userFinder.setEmail(user.getEmail());
        this.createNewUser(userFinder);
        return userFinder;
    }

    public Optional<UserApi>  deleteUser(Long id) throws UserApiException {
        Optional<UserApi> user = this.findOne(id);
        this.userRepository.delete(id);
        return user;
    }



}
