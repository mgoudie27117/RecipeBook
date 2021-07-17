package com.sweng.recipebook.models;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public abstract class SecurityHandler {

    public static final String SECRET = "AmazingRecipes";
    public static final int EXPIRATION = 1;

    public SecurityHandler() {
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
     * getUserName - Method to parse the username from the token.
     * 
     * @param token - User token.
     * @return - Username String.
     */
    public String getUserName(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userName").asString();
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
     * @return - True if verified, otherwise false.
     */
    public boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
            verifier.verify(token);
        } catch (TokenExpiredException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }
}
