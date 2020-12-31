package br.edu.ufcg.virtus.courseautomation.filters;

import br.edu.ufcg.virtus.courseautomation.services.JWTService;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends GenericFilterBean {

    public final static int TOKEN_INDEX = 7;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token not found or invalid");
            return;
        }
        String token = header.substring(TOKEN_INDEX);
        try {
            Jwts.parser().setSigningKey(JWTService.NOTHING).parsePlaintextJws(token).getBody();
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | PrematureJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
        chain.doFilter(request, response);
    }
}
