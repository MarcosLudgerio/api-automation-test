package br.edu.ufcg.virtus.courseautomation.users;


import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserDetailsDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserUpdateDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserWithoutPassDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.*;
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
    public void shouldFindOneUserTest() {
        UserDetailsDTO user = this.userService.createNewUser(new UserApi("Giovanna", "Giovanna12@email.com"));
        UserApi userReturned = this.userService.findOne(user.getId());
        Assertions.assertEquals(user.getName(), userReturned.getName());
    }

    @Test
    public void shouldUpdateUserUsingATokenTest() {
        UserApi userCrete = new UserApi("Daniel", "Daniel@email.com", "Daniel@123");
        UserDetailsDTO user = this.userService.createNewUser(userCrete);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(user.getEmail());
        userLoginDTO.setPassword(userCrete.getPassword());
        String token = this.jwtService.autentication(userLoginDTO);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(Optional.of("Tobias"), Optional.of("t0bias"), Optional.of("madison"), Optional.of("Não tem"),
                Optional.of("não tem"), Optional.of("tobias@email.com"), Optional.of("nãotem"));
        UserDetailsDTO userReturned = this.userService.updateUser(token, userUpdateDTO);
        Assertions.assertEquals(userReturned.getName(), userUpdateDTO.getName().get());
    }

    @Test
    public void shouldReturnUserByEmailTest() {
        UserDetailsDTO user = this.userService.createNewUser(new UserApi("Helena", "HelenaMeuAmor@email.com"));
        UserApi userReturned = this.userService.findByEmail(user.getEmail());
        Assertions.assertEquals(user.getName(), userReturned.getName());
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

    @Test
    public void shouldUpdateUserUsingWithFields() {
        UserApi userCrete = new UserApi("Rodrigo", "Rodrigo@email.com.br", "Rodrigo@123");
        UserDetailsDTO user = this.userService.createNewUser(userCrete);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(user.getEmail());
        userLoginDTO.setPassword(userCrete.getPassword());
        String token = this.jwtService.autentication(userLoginDTO);
        this.userService.updateUser(token, new UserUpdateDTO(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty()));
    }

    @Test
    public void shouldUpdateUserUsingWithFieldNameLastnameTest() {
        UserApi userCrete = new UserApi("Emanuel", "Emanuel@gmail.email.com.br", "Emanuel@123");
        UserDetailsDTO user = this.userService.createNewUser(userCrete);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(user.getEmail());
        userLoginDTO.setPassword(userCrete.getPassword());
        String token = this.jwtService.autentication(userLoginDTO);
        this.userService.updateUser(token, new UserUpdateDTO(Optional.of(user.getName()), Optional.of("Updated Pass"), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty()));
    }

    @Test
    public void shouldParserUserDTOTest() {
        UserApi userCreated = new UserApi("Lucas", "Lucas@gmail.email.com.br", "Test@123");
        UserDTO userDTO = new UserDTO(userCreated);
        userDTO.setBio(Optional.empty());
        userDTO.setUrlImage(Optional.empty());
        UserApi user = this.userService.fromDTO(userDTO);

        Assertions.assertEquals(userCreated.getName(), user.getName());

    }

    @Test
    public void shouldParserUserWithoutPassDTOOTest() {
        UserApi userCreated = new UserApi("Thiago", "Thiago@gmail.email.com.br", "Test@123");
        UserApi user = this.userService.fromDTO(new UserWithoutPassDTO(userCreated));
        Assertions.assertEquals(userCreated.getName(), user.getName());
    }


    @Test
    public void shouldValidateUser() {
        UserApi userCrete = new UserApi("Mariana", "Mariana@email.com", "Mariana@123");
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
    public void shouldCreateANewUserSuccessfulTest() {
        UserApi userApi = new UserApi("Miguel", "miguel@gmail.com");
        UserDetailsDTO userDetailsDTO = this.userService.createNewUser(userApi);
        Assertions.assertEquals(userDetailsDTO.getEmail(), userApi.getEmail());
    }

    @Test
    public void shouldFindAllUsersTest() {
        this.userService.createNewUser(new UserApi("Julia", "Julia12@email.com"));
        this.userService.createNewUser(new UserApi("Isabella", "Isabella23@email.com"));
        this.userService.createNewUser(new UserApi("Lucas", "Lucas@email.com"));
        Assertions.assertEquals(this.userService.findAllUsers().size(), this.userService.findAllUsersApi().size());
    }

    @Test
    public void shouldThrowUserAlreadyExistsExceptionTest() {
        UserDetailsDTO user = this.userService.createNewUser(new UserApi("Letícia", "let@email.com"));
        try {
            this.userService.createNewUser(new UserApi("Melissa", "let@email.com"));
            Assertions.fail("Cannot create a new user with the same e-mail");
        } catch (UserAlreadyExistsException ignored) {
        }
    }

    @Test
    public void shouldThrowUserNotFoundExceptionWhenUpdateUserWithEmailNullTest() {
        try {
            UserUpdateDTO userUpdateDTO = new UserUpdateDTO(Optional.of("Bianca"), Optional.of("t0bias"), Optional.of("Bianca"), Optional.of("Não tem"),
                    Optional.of("não tem"), Optional.of("Bianca@email.com"), Optional.of("nãotem"));
            this.userService.updateUser("", userUpdateDTO);
            Assertions.fail("Id empty");
        } catch (TokenInvalidException ignored) {
        }
    }


    @Test
    public void shouldCreateAndRecoveryAUser() {
        UserApi userApi = new UserApi("Leonardo", "Leonardo@email.com", "Leonardo@123");
        UserDetailsDTO user = this.userService.createNewUser(userApi);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(user.getEmail());
        userLoginDTO.setPassword(userApi.getPassword());
        String token = this.jwtService.autentication(userLoginDTO);
        UserDetailsDTO userReturned = this.userService.findOne(token);
        Assertions.assertEquals(userReturned.getName(), user.getName());
    }


}
