package org.bashtan.MyApps.configs.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import org.bashtan.MyApps.data.services.interfaces.BlacklistServiceInterface;
import org.bashtan.MyApps.exceptions.jwt.JwtTokenExpiredException;
import org.bashtan.MyApps.exceptions.jwt.JwtTokenInvalidException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtUtil {

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final BlacklistServiceInterface blacklistServiceInterface;


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) throws JwtTokenInvalidException {
        try {
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException("JWT Token has expired");
        } catch (SignatureException e) {
            throw new JwtTokenInvalidException("JWT signature does not match locally computed signature.");
        } catch (MalformedJwtException e) {
            throw new JwtTokenInvalidException("Invalid JWT token");
        } catch (Exception e) {
            throw new JwtTokenInvalidException("Error parsing JWT token");
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30)) // 30 days
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && !blacklistServiceInterface.isTokenBlacklisted(token));
    }

    public void logOut(String token) {
        blacklistServiceInterface.addTokenToBlacklist(token);
    }
}
