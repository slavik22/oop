package com.example.back.servlets;

import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.back.dao.UserDAO;
import com.example.back.dto.UserLoginDTO;
import com.example.back.dto.UserLoginResponse;
import com.example.back.model.User;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.stream.Collectors;

import com.google.gson.Gson;


@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestData = request.getReader().lines().collect(Collectors.joining());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        UserLoginDTO loginDTO = gson.fromJson(requestData, UserLoginDTO.class);

        User user = userDAO.loginUser(loginDTO.getEmail(), loginDTO.getPassword());

        if (user != null) {
            Algorithm algorithm = Algorithm.HMAC256("baeldung");

            String jwtToken = JWT.create()
                    .withIssuer("your_issuer")
                    .withSubject(String.valueOf(user.getId()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 360000000)) // Token valid for 1 hour
                    .sign(algorithm);

            UserLoginResponse userLoginResponse = new UserLoginResponse(user.getId(), user.getName(), user.getEmail(), user.isAdmin(), jwtToken);

            response.setContentType("application/json");
            String json = gson.toJson(userLoginResponse);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid credentials");
        }
    }
}
