package br.edu.ufcg.virtus.courseautomation.users;

import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserDetailsDTO;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void createANewUserSuccessful(){
        UserApi userApi = new UserApi("Mark", "email@email.com", "p@ssW0rd");
        UserDetailsDTO object = this.userService.createNewUser(userApi);
        Assertions.assertEquals("email@email.com", object.getEmail());
    }
}
