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
        UserDetailsDTO userDetailsDTO = this.userService.createNewUser(userApi);
        Assertions.assertEquals(userDetailsDTO.getEmail(), userApi.getEmail());
    }

    @Test
    public void shouldFindAllUsersTest() {
        this.userService.createNewUser(new UserApi("Mark", "mark1@email.com"));
        this.userService.createNewUser(new UserApi("Mario", "mario@email.com"));
        this.userService.createNewUser(new UserApi("Clark", "clark@email.com"));
        Assertions.assertEquals(this.userService.findAllUsers().size(), this.userService.findAllUsersApi().size());
    }

    @Test
    public void shouldFindOneUserTest() {
        UserDetailsDTO user = this.userService.createNewUser(new UserApi("Mark", "mark123@email.com"));
        UserApi userReturned = this.userService.findOne(user.getId());
        Assertions.assertEquals(user.getName(), userReturned.getName());
    }

    @Test
    public void shouldReturnUserByEmailTest() {
        UserDetailsDTO user = this.userService.createNewUser(new UserApi("Mark", "mark2@email.com"));
        UserApi userReturned = this.userService.findByEmail(user.getEmail());
        Assertions.assertEquals(user.getName(), userReturned.getName());
    }

    @Test
    public void shouldUpdateUserTest() {
        UserDetailsDTO user = this.userService.createNewUser(new UserApi("Mark", "mark2weq@email.com"));
        user.setName("Name updated");
        UserApi userReturned = (UserApi) this.userService.updateUser(user.getEmail(), user);
        Assertions.assertEquals(user.getName(), userReturned.getName());
    }

    @Test
    public void shouldThrowUserAlreadyExistsExceptionTest() {
        UserDetailsDTO user = this.userService.createNewUser(new UserApi("Mark", "mark2weq@email.com"));
        UserDetailsDTO user2 = this.userService.createNewUser(new UserApi("Mark", "mark2weq@email.com"));

        Assertions.assertEquals(user.getName(), user2.getName());
    }

    @Test
    public void shouldValidateUser() {
        UserApi userCrete =new UserApi("Mark", "markTestLogin@email.com", "Test@123");
        UserDetailsDTO user = this.userService.createNewUser(userCrete);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(user.getEmail());
        userLoginDTO.setPassword(userCrete.getPassword());
        String token = this.jwtService.autentication(userLoginDTO);
        Optional<String> id = this.jwtService.restoreAccount(token);
        this.userService.validateUser(id);
    }

    @Test
    public void shouldCreateAndRecoveryAUser() {
        UserApi userApi = new UserApi("Mark", "markTestLoginqqweqweqwe@email.com", "Test@123");
        UserDetailsDTO user = this.userService.createNewUser(userApi);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(user.getEmail());
        userLoginDTO.setPassword(userApi.getPassword());
        String token = this.jwtService.autentication(userLoginDTO);
        UserDetailsDTO userReturned = this.userService.findOne(token);
        Assertions.assertEquals(userReturned.getName(), user.getName());
    }


}
