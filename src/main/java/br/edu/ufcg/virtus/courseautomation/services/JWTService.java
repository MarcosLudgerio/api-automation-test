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
        UserApi userFinder = userService.findOneAuth(user.getId());
        if (user.getPassword().equals(userFinder.getPassword()) && userFinder.getEmail().equals(user.getEmail()))
            return this.generateToken(user);
        return errorMessage;
    }

    private String generateToken(UserDTO user) {
        String token = Jwts.builder().setSubject(user.getEmail()).signWith(SignatureAlgorithm.HS512, NOTHING).setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).compact();
        return token;
    }

    public Optional<String> restoreAccount(String headerAuthorization) {
        if (headerAuthorization == null)
            throw new SecurityException("UNAUTHORIZED");
        String subject = "";
        subject = Jwts.parser().setSigningKey(NOTHING).parseClaimsJws(headerAuthorization).getBody().getSubject(); // subject return email
        return Optional.of(subject);
    }
}
