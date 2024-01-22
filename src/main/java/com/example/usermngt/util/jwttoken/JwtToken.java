package com.example.usermngt.util.jwttoken;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.usermngt.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtToken {
    private static final String secret = "colin";
    private static final Long expiration = 1209600L;

    public static String generateToken(User user) {
        Date expireDate = new Date(System.currentTimeMillis() + expiration * 1000);

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)
                .withClaim("username", user.getUsername())
                .withClaim("password", user.getPassword())
                .withExpiresAt(expireDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(secret));
        return token;
    }

    public static DecodedJWT verifyToken(String token) {
        DecodedJWT jwt = null;

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            System.out.println("token is incorrect: " + e.getMessage());
        }
        return jwt;
    }

}
