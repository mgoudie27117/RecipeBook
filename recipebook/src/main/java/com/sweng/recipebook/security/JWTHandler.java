package com.sweng.recipebook.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sweng.recipebook.models.RecipeBookUser;
import java.util.Date;

public class JWTHandler {

    public static final String SECRET = "AmazingRecipes";
    public static final int EXPIRATION = 1;

    public JWTHandler() {

    }

    // TEST ME NEW
    public String generateToken(RecipeBookUser user) {
        return JWT.create().withIssuer("auth0").withClaim("userId", user.getUserId())
                .withClaim("userName", user.getUserId()).withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .withExpiresAt(new Date(System.currentTimeMillis() + (EXPIRATION * 60 * 1000)))
                .sign(Algorithm.HMAC256(SECRET));
    }

    // TEST ME NEW
    public String refreshToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        RecipeBookUser user = new RecipeBookUser(jwt.getClaim("firstName").toString(),
                jwt.getClaim("lastName").toString(), "", 0, jwt.getClaim("userName").toString());
        return generateToken(user);
    }

    // TEST ME NEW
    public JWTStatus verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
            verifier.verify(token);
        } catch (TokenExpiredException e) {
            return JWTStatus.EXPIRED;
        } catch (JWTVerificationException e) {
            return JWTStatus.INVALID;
        }
        return JWTStatus.VALID;
    }
}
