package com.sweng.recipebook.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sweng.recipebook.models.RecipeBookUser;
import com.sweng.recipebook.models.User;
import java.util.Date;

/**
 * JWTHandler - Class to handle methods related to token handling.
 */
public class JWTHandler {

    public static final String SECRET = "AmazingRecipes";
    public static final int EXPIRATION = 1;

    public JWTHandler() {

    }

    /**
     * generateToken - Method to generate a token String for the given User object.
     * 
     * @param user - User object to create token for.
     * @return - Token String
     */
    public String generateToken(User user) {
        return JWT.create().withIssuer("auth0").withClaim("userId", user.getUserId())
                .withClaim("userName", user.getUserId()).withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .withExpiresAt(new Date(System.currentTimeMillis() + (EXPIRATION * 60 * 1000)))
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * getUserId - Method to parse the user id from the token.
     * 
     * @param token - User token.
     * @return - User id int.
     */
    public int getUserId(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userId").asInt();
    }

    /**
     * refreshToken - Method to take the old token and generate a new one with
     * refreshed expiration.
     * 
     * @param token - Old token String.
     * @return - New token String to replace the given token.
     */
    public String refreshToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        User user = new RecipeBookUser(jwt.getClaim("firstName").toString(), jwt.getClaim("lastName").toString(), "", 0,
                jwt.getClaim("userName").toString());
        return generateToken(user);
    }

    /**
     * verifyToken - Method to verify a given token.
     * 
     * @param token - Token string.
     * @return - JWTStatus of valid if verified, otherwise invalid or expired if
     *         detected.
     */
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
