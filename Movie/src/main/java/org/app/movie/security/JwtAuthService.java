package org.app.movie.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtAuthService implements AuthService {
    private final BaseJwtService baseJwtService;
    private final JwtCredentials jwtCredentials;

    @Override
    public Optional<Authentication> getAuthentication(HttpServletRequest httpServletRequest) {
       String header =  httpServletRequest.getHeader("Authorization");
        if(header !=null&& header.startsWith("Bearer ")){
            String token = header.substring(7);
            try {
                Jws<Claims> claimsJws = baseJwtService.parseToken(token);
                return Optional.of(getAuthentication(claimsJws.getPayload()));
            }catch (JwtException e){
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private Authentication getAuthentication(Claims claims){

        jwtCredentials.setUserId(claims.get("userId").toString());
        jwtCredentials.setName(claims.get("name").toString());
        jwtCredentials.setSurname(claims.get("surname").toString());
        jwtCredentials.setRole(claims.get("role").toString());

        System.out.println(jwtCredentials);


        return  new UsernamePasswordAuthenticationToken(null,jwtCredentials,null);
    }
}
