package br.edu.ufcg.virtus.courseautomation.dtos.userViewDTO;

import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import lombok.Data;

@Data
public class UserViewDTO {
    private String name;
    private String lastname;
    private String bio;
    private String site;
    private String email;
    private String urlImage;

    public UserViewDTO(UserApi user){
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.bio = user.getBio();
        this.site = user.getSite();
        this.email = user.getEmail();
        this.urlImage = user.getUrlImageProfile();
    }
}
