package com.flat.Service.Impl;

import com.flat.Service.JwtService;
import com.flat.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    public String generateToken(UserDetails userDetails){
        try
        {
            return Jwts.builder().setSubject(userDetails.getUsername())
                    .setIssuedAt( new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 *60))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)
                    .compact();
        }catch (Exception ex){
            System.out.println(ex);
            return  "Token not Generated";
        }

    }

    public String generateRefreshToken(Map<String,Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
                .setIssuedAt( new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T extractClaim(String token , Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaim(token);
        return claimsResolvers.apply(claims);

    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.
                parserBuilder().
                setSigningKey(getSignKey())
                .build().
                parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey(){

        String key = "55f7495b9347d09e0f7dc1a7e8a554c39bc3f759ad52411a7b4dbefcd5727d93314f6a6b725ad9f19ce1a2db518ca6f8e90596734429c87d41c2498f51794b14c7b4a9da98510a28120d7dbf4f7de091b381c79a6c72ddf03d1c9d8c4d1c6b4a82e45f195b2b37e91db4836e5805c32a14115b2edb87421a582c2761d78e6a89f37cbf4a1d359bd93a5b45993a562fd6391c57af46de5813b2cf619cf4d29edb28497b17c6ae58164c13dcd8129e4f67b6b19a8d2f513fa26e392fe09a8e61c74649b14af1d03d95b68217e2f973e2a6d8d8197c8b6b1ae497c1f8d3e59c72ac8a3b19c47e41d56a294b97f6c81e67b582c37a961d8f3a7492c5b6df3a7f96d21\n";
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public boolean isTokenExpired(String token)
    {
        return extractClaim(token, Claims:: getExpiration).before(new Date());
    }
}
