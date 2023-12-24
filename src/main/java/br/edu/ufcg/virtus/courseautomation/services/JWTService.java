package br.edu.ufcg.virtus.courseautomation.services;

import br.edu.ufcg.virtus.courseautomation.dtos.usersDTO.UserLoginDTO;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenException;
import br.edu.ufcg.virtus.courseautomation.exceptions.TokenInvalidException;
import br.edu.ufcg.virtus.courseautomation.exceptions.UserApiException;
import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JWTService {
    public static final String NOTHING = "issonaoimporta";

    @Autowired
    private UserService userService;

    public String autentication(UserLoginDTO user) throws UserApiException, TokenException {
        String errorMessage = "Falha ao tentar efetuar o login, verifique os dados e tente novamente";
        UserApi userFinder = userService.findByEmail(user.getEmail());
        if (!user.getPassword().equals(userFinder.getPassword()))
            throw new TokenException(errorMessage);
        if (user.getPassword().equals(userFinder.getPassword()) && userFinder.getEmail().equals(user.getEmail()))
            return this.generateToken(user);
        return errorMessage;
    }

    private String generateToken(UserLoginDTO user) {
        String token = Jwts.builder().setSubject(user.getEmail()).signWith(SignatureAlgorithm.HS512, NOTHING).setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).compact();
        return token;
    }

    public Optional<String> restoreAccount(String headerAuthorization) throws UserApiException, TokenException {
        if (headerAuthorization == null || !headerAuthorization.startsWith("eyJhbGciOiJIUzUxMiJ9"))
            throw new TokenInvalidException("Token não validado, verifique os dados e tente novamente");
        String subject = "";
        try {
            subject = Jwts.parser().setSigningKey(NOTHING).parseClaimsJws(headerAuthorization).getBody().getSubject(); // subject return email
        } catch (SignatureException | ExpiredJwtException ex) {
            throw new TokenInvalidException("Token expirado ou assinatura não encontrada, por favor refaça login e tente novamente");
        }
        return Optional.of(subject);
    }
}
