package com.example.usermngt.util.jwttoken;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.usermngt.util.Result;
import com.example.usermngt.util.ResultEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

@Slf4j
@WebFilter(filterName = "JwtFilter", urlPatterns = "/api/v1/*")
public class JwtFilter implements Filter, jakarta.servlet.Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final ObjectMapper objectMapper = new ObjectMapper();


//        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");


        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
        } else {
            // 获取token
            final String token = request.getHeader("token");

            if (token == null) {
                Result result = new Result(ResultEnum.NO_TOKEN_FOUND, "");
                String str = objectMapper.writeValueAsString(result);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().print(str);

                return;
            }

            DecodedJWT jwt = JwtToken.verifyToken(token);
            if (jwt == null) {
                Result result = new Result(ResultEnum.TOKEN_ILLEGAL, "");
                String str = objectMapper.writeValueAsString(result);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().print(str);
                return;
            }

            Map<String, Claim> user = jwt.getClaims();
            String username = user.get("username").asString();
            String password = user.get("password").asString();

            request.setAttribute("username", username);
            request.setAttribute("password", password);
            chain.doFilter(req, res);
        }
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}
