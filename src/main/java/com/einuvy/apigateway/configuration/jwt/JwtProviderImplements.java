package com.einuvy.apigateway.configuration.jwt;

import com.einuvy.apigateway.configuration.UserPrincipal;
import com.einuvy.apigateway.models.User;
import com.einuvy.apigateway.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import static com.einuvy.apigateway.utils.SecurityUtils.extractAuthTokenFromRequest;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

@Component
public class JwtProviderImplements implements JwtProvider{

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.eexpiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    @Override
    public String generateToken(UserPrincipal auth){
        String autorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(joining(","));

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(UTF_8));

        /*Instant now = Instant.now();
        Instant expiration = now.plus(Duration.ofMillis(JWT_EXPIRATION_IN_MS));*/


        return Jwts.builder()
                .setSubject(auth.getUsername())
                .claim("roles", autorities)
                .claim("userId", auth.getId())
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION_IN_MS))
                .signWith(key, HS512)
                .compact();
    }

    @Override
    public String generateToken(User user){
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(UTF_8));


        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRole())
                .claim("userId", user.getId())
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION_IN_MS))
                .signWith(key, HS512)
                .compact();
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request){
        Claims claims = extractClaims(request);
        if(claims == null){
            return null;
        }
        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(toSet());

        UserDetails userDetails = UserPrincipal.builder()
                .username(username)
                .authorities(authorities)
                .id(userId)
                .build();

        if (username==null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request){
        Claims claims = extractClaims(request);

        if (claims==null){
            return false;
        }

        if (claims.getExpiration().before(new Date())){
            return false;
        }

        return true;
    }

    private Claims extractClaims(HttpServletRequest request){
        String token = extractAuthTokenFromRequest(request);

        if (token == null){
            return null;
        }

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
