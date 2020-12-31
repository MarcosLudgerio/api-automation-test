package br.edu.ufcg.virtus.courseautomation.services;

import br.edu.ufcg.virtus.courseautomation.dtos.UserDTO;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.filters.TokenFilter;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JWTService {
    public static final String NOTHING = "issonaoimporta";

    @Autowired
    private UserService userService;

    public String autentication(UserDTO user) throws UserApiException {
        String errorMessage = "Login failed, please try again";
        UserApi userFinder = userService.findOne(user.getId());
        if (user.getPassword().equals(userFinder.getPassword()) && userFinder.getEmail().equals(user.getEmail()))
            return this.generateToken(user);
        return errorMessage;
    }

    private String generateToken(UserDTO user) {
        String token = Jwts.builder().setSubject(user.getEmail()).signWith(SignatureAlgorithm.HS512, NOTHING).setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).compact();
        return token;
    }

    public Optional<String> restoreAccount(String headerAuthorization) {
        System.out.println("headerAuthorization " + headerAuthorization);
        if (headerAuthorization == null || !headerAuthorization.startsWith("Bearer ")) throw new SecurityException();
        String token = headerAuthorization.substring(TokenFilter.TOKEN_INDEX);
        System.out.println("token " + token);
        String subject = "";
        subject = Jwts.parser().setSigningKey(this.NOTHING).parseClaimsJws(token).getBody().getSubject(); // subject retorna o email
        System.out.println("subject" + subject);
        return Optional.of(subject);
    }
}
