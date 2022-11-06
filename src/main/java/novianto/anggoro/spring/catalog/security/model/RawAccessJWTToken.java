package novianto.anggoro.spring.catalog.security.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.security.Key;

public class RawAccessJWTToken implements Token{

    private String token;

    public RawAccessJWTToken(String token) {
        this.token = token;
    }

    public Jws<Claims> parseClaim(Key signingKey){
    return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(this.token);

    }

    @Override
    public String getToken() {
        return this.token;
    }
}
