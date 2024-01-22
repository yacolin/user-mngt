package com.example.usermngt.util;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.usermngt.util.jwttoken.JwtToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String token = request.getHeader("token");
        DecodedJWT jwt = JwtToken.verifyToken(token);

        Result result = validateToken(token, jwt);
        if (result != null) {
            sendErrorResponse(response, result);
            return false;
        }

//        if (token == null) {
//            Result result = new Result(ResultEnum.NO_TOKEN_FOUND, "");
//            sendErrorResponse(response, result);
//
//            return false;
//        }
//
//        if (jwt == null) {
//            Result result = new Result(ResultEnum.TOKEN_ILLEGAL, "");
//            sendErrorResponse(response, result);
//
//            return false;
//        }
//
//        if (jwt.getExpiresAt().before(new Date())) {
//            Result result = new Result(ResultEnum.TOKEN_EXPIRED, "");
//            sendErrorResponse(response, result);
//
//            return false;
//        }



        Map<String, Claim> user = jwt.getClaims();
        String username = user.get("username").asString();
        String password = user.get("password").asString();

        request.setAttribute("username", username);
        request.setAttribute("password", password);
        return true;
    }


    // 封装重复代码，设置返回状态和返回内容
    private void sendErrorResponse(HttpServletResponse response, Result result) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writeValueAsString(result);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(str);
    }

    // 处理token，根据token错误的不同，返回不同的错误信息
    private Result validateToken(String token, DecodedJWT jwt) {
        if (token == null) {
            return new Result(ResultEnum.NO_TOKEN_FOUND, "");
        }
        if (jwt == null) {
            return new Result(ResultEnum.TOKEN_ILLEGAL, "");
        }
        if (jwt.getExpiresAt().before(new Date())) {
            return new Result(ResultEnum.TOKEN_EXPIRED, "");
        }
        return null;
    }
}
