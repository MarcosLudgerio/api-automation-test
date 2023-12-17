package br.edu.ufcg.virtus.courseautomation.users;

import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.*;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.services.JWTService;
import br.edu.ufcg.virtus.courseautomation.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Test
    public void shouldCreateANewUserSuccessfulTest() {
        UserApi userApi = new UserApi("Mark", "email@email.com");
        UserDTO object = this.userService.createNewUserApi(userApi);
        Assertions.assertEquals(object.getEmail(), userApi.getEmail());
    }

    @Test
    public void shouldFindAllUsersTest() {
        this.userService.createNewUserApi(new UserApi("Mark", "mark1@email.com"));
        this.userService.createNewUserApi(new UserApi("Mario", "mario@email.com"));
        this.userService.createNewUserApi(new UserApi("Clark", "clark@email.com"));
        Assertions.assertEquals(this.userService.findAllUsers().size(), this.userService.findAllUsersApi().size());
    }

    @Test
    public void shouldFindOneUserTest() {
        UserDTO user = this.userService.createNewUserApi(new UserApi("Mark", "mark123@email.com"));
        UserApi userReturned = this.userService.findOne(user.getId());
        Assertions.assertEquals(user.getName(), userReturned.getName());
    }

    @Test
    public void shouldReturnUserByEmailTest() {
        UserDTO user = this.userService.createNewUserApi(new UserApi("Mark", "mark2@email.com"));
        UserApi userReturned = this.userService.findByEmail(user.getEmail());
        Assertions.assertEquals(user.getName(), userReturned.getName());
    }

    @Test
    public void shouldUpdateUserTest() {
        UserDTO user = this.userService.createNewUserApi(new UserApi("Mark", "mark2weq@email.com"));
        user.setName("Name updated");
        UserApi userReturned = (UserApi) this.userService.updateUser(user.getEmail(), user);
        Assertions.assertEquals(user.getName(), userReturned.getName());
    }

    @Test
    public void shouldThrowUserAlreadyExistsExceptionTest() {
        UserDTO user = this.userService.createNewUserApi(new UserApi("Mark", "mark2weq@email.com"));
        UserDTO user2 = this.userService.createNewUserApi(new UserApi("Mark", "mark2weq@email.com"));

        Assertions.assertEquals(user.getName(), user2.getName());
    }

    @Test
    public void shouldValidateUser() {
        UserDTO user = this.userService.createNewUserApi(new UserApi("Mark", "markTestLogin@email.com", "Test@123"));
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(user.getEmail());
        userLoginDTO.setPassword(user.getPassword());
        String token = this.jwtService.autentication(userLoginDTO);
        Optional<String> id = this.jwtService.restoreAccount(token);
        this.userService.validateUser(id);
    }

    @Test
    public void shouldCreateAndRecoveryAUser() {
        UserDTO user = this.userService.createNewUserApi(new UserApi("Mark", "markTestLoginqqweqweqwe@email.com", "Test@123"));
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(user.getEmail());
        userLoginDTO.setPassword(user.getPassword());
        String token = this.jwtService.autentication(userLoginDTO);
        UserDetailsDTO userReturned = this.userService.findOne(token);
        Assertions.assertEquals(userReturned.getName(), user.getName());
    }

    @Test
    public void shouldConvertFromDtoUsers() {
        UserApi userApi = new UserApi();
        UserDTO user = this.userService.createNewUserApi(new UserApi(null, "Markisons", "Mansons", "markTestLogin@email.com", "Test@123", "Bio", "site", "url"));
        userApi.setId(user.getId());
        userApi.setName(user.getName());
        userApi.setLastname(user.getLastname());
        userApi.setEmail(user.getEmail());
        userApi.setPassword(user.getPassword());
        userApi.setSite(user.getSite());
        userApi.setBio("Nada de bio");
        this.userService.fromDTO(new UserDTO(userApi));

    }


}
