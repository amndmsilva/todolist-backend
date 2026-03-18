package com.amandasantos.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.amandasantos.todolist.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        var servletPath = request.getServletPath();

        //Liberar criação de usuário
        if (servletPath.startsWith("/users")) {
            filterChain.doFilter(request, response);
            return;
        }

        var authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Basic ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {

            var base64Credentials = authorization.substring("Basic ".length());
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            var credentials = new String(decodedBytes);

            var values = credentials.split(":", 2);
            var email = values[0];
            var password = values[1];

            var user = userRepository.findByEmail(email);

            if (user == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            var result = BCrypt.verifyer()
                    .verify(password.toCharArray(), user.getPassword());

            if (!result.verified) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // salva o id do usuário na request
            request.setAttribute("user_id", user.getId());

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}