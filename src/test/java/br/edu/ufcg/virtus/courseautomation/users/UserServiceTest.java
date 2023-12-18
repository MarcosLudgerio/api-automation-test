package br.edu.ufcg.virtus.courseautomation.users;

import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserDetailsDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserLoginDTO;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenInvalidException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserAlreadyExistsException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserNotFoundException;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.services.JWTService;
import br.edu.ufcg.virtus.courseautomation.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void shouldValidateUser() {
        UserApi userCrete = new UserApi("Mark", "markTestLogin@email.com", "Test@123");
        UserDetailsDTO user = this.userService.createNewUser(userCrete);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(user.getEmail());
        userLoginDTO.setPassword(userCrete.getPassword());
        String token = this.jwtService.autentication(userLoginDTO);
        Optional<String> id = this.jwtService.restoreAccount(token);
        UserApi userReturned = this.userService.validateUser(id);
        Assertions.assertEquals(userReturned.getName(), userCrete.getName());
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

    @Test
    public void shouldThrowUserAlreadyExistsExceptionTest() {
        UserDetailsDTO user = this.userService.createNewUser(new UserApi("Mark", "emailjacadastrado@email.com"));
        try {
            this.userService.createNewUser(new UserApi("Mark", "emailjacadastrado@email.com"));
            Assertions.fail("Cannot create a new user with the same e-mail");
        } catch (UserAlreadyExistsException ignored) {
        }
    }

    @Test
    public void shouldThrowTokenInvalidExceptionTest() {
        try {
            this.userService.findOne("");
            Assertions.fail("Token is null");
        } catch (TokenInvalidException ignored) {
        }
    }

    @Test
    public void shouldThrowTokenExceptionTest() {
        try {
            this.userService.findOne((String) null);
            Assertions.fail("Token Invalid");
        } catch (TokenInvalidException ignored) {
        }
    }

    @Test
    public void shouldThrowUserNotFoundExceptionTest() {
        try {
            this.userService.findOne(-1L);
            Assertions.fail("User not found");
        } catch (UserApiException ignored) {
        }
    }

    @Test
    public void shouldThrowUserNotFoundExceptionWhenUpdateUserWithEmailNullTest() {
        try {
            UserDetailsDTO user = this.userService.createNewUser(new UserApi("Mark", null));
            user.setName("Name updated");
            this.userService.updateUser("", user);
            Assertions.fail("Id empty");
        } catch (UserNotFoundException ignored) {
        }
    }

    @Test
    public void shouldThrowUserNotFoundExceptionWhenUpdateUserWithEmailNotFoundTest() {
        try {
            UserDetailsDTO user = this.userService.createNewUser(new UserApi("Mark", "mark.mail@mail.com"));
            user.setName("Name updated");
            this.userService.updateUser(user.getEmail() + "User updated", user);
            Assertions.fail("Email not found");
        } catch (UserApiException ignored) {
        }
    }

    @Test
    public void shouldThrownExceptionUserNotFoundWhenValidateUserAndIdIsNull() {
        try {
            this.userService.validateUser(null);
            Assertions.fail("Id null");
        } catch (UserNotFoundException ignored) {
        }
    }

    @Test
    public void shouldThrownExceptionUserNotFoundWhenValidateUserAndUserNotFound() {
        try {
            this.userService.validateUser(Optional.of("email@email.com"));
            Assertions.fail("Email not found");
        } catch (UserApiException ignored) {
        }
    }

    @Test
    public void shouldThrownExceptionUserNotFoundWhenValidateUserAndIdIsNotPresent() {
        try {
            this.userService.validateUser(Optional.empty());
            Assertions.fail("Id null");
        } catch (UserNotFoundException ignored) {
        }
    }

}
