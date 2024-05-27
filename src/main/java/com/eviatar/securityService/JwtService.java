package com.eviatar.securityService;

import com.eviatar.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "cbe77d3cb521b04805d664c687367ae360720c8a1d95ace20c7e5ebf27e7a9ff";

    private Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getGenericClaim(String token, Function<Claims, T> resolver) {
        Claims claims = getClaims(token);
        return resolver.apply(claims);
    }

    public String getUserName(String token) {
        return getGenericClaim(token, claims -> claims.getSubject());
    }

    private boolean isTokenExpierd(String token) {
        return getGenericClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean isValid(String token, UserDetails userDetails) {
        return (getUserName(token).equals(userDetails.getUsername()) && !isTokenExpierd(token));
    }

    public String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSignKey())
                .compact();

    }

    private SecretKey getSignKey() {
        byte[] decode = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }
}
