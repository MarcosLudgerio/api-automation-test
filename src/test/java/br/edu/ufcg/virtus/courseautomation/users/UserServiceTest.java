package br.edu.ufcg.virtus.courseautomation.users;

import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserDetailsDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserUpdateDTO;
import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserWithoutPassDTO;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import br.edu.ufcg.virtus.courseautomation.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void createANewUserSuccessfulTest(){
        UserApi userApi = new UserApi("Mark", "email@email.com");
        UserDTO object = this.userService.createNewUserApi(userApi);
        Assertions.assertEquals(object.getEmail(), userApi.getEmail());
    }

    @Test
    public void findAllUsersTest(){
        this.userService.createNewUserApi(new UserApi("Mark", "mark@email.com"));
        this.userService.createNewUserApi(new UserApi("Mario", "mario@email.com"));
        this.userService.createNewUserApi(new UserApi("Clark", "clark@email.com"));
        Assertions.assertEquals(3, this.userService.findAllUsersApi().size());
    }

    @Test
    public void findOneUserTest(){
        UserDTO user = this.userService.createNewUserApi(new UserApi("Mark", "mark@email.com"));
        UserApi userReturned = this.userService.findOne(user.getId());
        Assertions.assertEquals(user.getName(), userReturned.getName());
    }

    @Test
    public void findByEmailTest(){
        UserDTO user = this.userService.createNewUserApi(new UserApi("Mark", "mark@email.com"));
        UserApi userReturned = this.userService.findByEmail(user.getEmail());
        Assertions.assertEquals(user.getName(), userReturned.getName());
    }

    @Test
    public void userUpdateDtoTest(){
        UserDTO user = this.userService.createNewUserApi(new UserApi("Mark", "mark@email.com"));
        UserApi userReturned = this.userService.findByEmail(user.getEmail());
        user.setName("Clark Kent");
        this.userService.updateUser(userReturned, user);
        Assertions.assertEquals(user.getName(), userReturned.getName());
    }


}
