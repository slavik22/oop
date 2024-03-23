package com.example.back.servlets;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.back.dao.UserDAO;
import com.example.back.dto.UserLoginDTO;
import com.example.back.dto.UserLoginResponse;
import com.example.back.dto.UserRegisterDTO;
import com.example.back.model.Account;
import com.example.back.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@WebServlet("/user/accounts")
public class UserGetAccountsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int userId = (int) request.getAttribute("userId");

        List<Account> accounts = userDAO.getUserAccounts(userId);

        if (accounts != null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();


            response.setContentType("application/json");
            String json = gson.toJson(accounts);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Not found");
        }
    }
}
