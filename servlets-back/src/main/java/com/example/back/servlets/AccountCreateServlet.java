package com.example.back.servlets;

import com.example.back.dao.AccountDAO;
import com.example.back.dto.AccountCreateDTO;
import com.example.back.model.Account;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/account/create")
public class AccountCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AccountDAO accountDAO = new AccountDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getAttribute("userId");

        BufferedReader reader = request.getReader();
        AccountCreateDTO accountCreateRequest = gson.fromJson(reader, AccountCreateDTO.class);

        Account account = new Account();
        account.setNumber(accountCreateRequest.getNumber());
        account.setSum(0);
        account.setLocked(false);

        boolean success = accountDAO.createAccount(account, userId);

        if (success) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Account created successfully");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to create account");
        }
    }
}
