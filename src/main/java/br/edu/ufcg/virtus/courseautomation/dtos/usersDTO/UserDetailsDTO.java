package br.edu.ufcg.virtus.courseautomation.dtos.usersDTO;

import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailsDTO {

    private Long id;

    private String name;
    private String lastname;
    private String bio;
    private String site;
    private String email;
    private String urlImage;
    private List<String> posts;


    public UserDetailsDTO(String name, String lastname, String bio, String site, String email, String urlImage) {
        this.name = name;
        this.lastname = lastname;
        this.bio = bio;
        this.site = site;
        this.email = email;
        this.urlImage = urlImage;

    }
    public UserDetailsDTO(UserApi userApi) {
        this(userApi.getName(), userApi.getLastname(), userApi.getBio(), userApi.getSite(), userApi.getEmail(), userApi.getUrlImageProfile());
    }

    public UserDetailsDTO(UserApi userApi, List<String> posts) {
        this(userApi.getName(), userApi.getLastname(), userApi.getBio(), userApi.getSite(), userApi.getEmail(), userApi.getUrlImageProfile());
        this.posts = posts;
    }

}
