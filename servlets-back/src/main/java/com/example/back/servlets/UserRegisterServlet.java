package com.example.back.servlets;

import com.example.back.dao.UserDAO;
import com.example.back.dto.UserRegisterDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;


@WebServlet("/user/register")
public class UserRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().collect(Collectors.joining());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        UserRegisterDTO registerDTO = gson.fromJson(requestData, UserRegisterDTO.class);

        boolean success = userDAO.registerUser(registerDTO.getName(), registerDTO.getEmail(), registerDTO.getPassword());

        if (success) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("User registered successfully");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to register user");
        }
    }
}
