package com.example.sellcar_spring.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {


    /*This method will use to generate Secret Key, that used to secure communication like token based authentication system*/
    private SecretKey getSigningKey(){
        String SECRET = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /* This method is used to extract and decode all claims or payload from JWT*/
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    /*This method allow for the extraction of the specific claim of any type from the JWT*/
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*this method provide the straightForward method to extract the username from the JWTtoken */
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    /*this method provide a way to extract the expiration data from a JWT token*/
    private Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /*This method is responsible for creating a JWT Token with specified claims and the user details including additional custom claims*/
    private String generateToken(Map<String,Object> extractClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigningKey() ,SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

}
