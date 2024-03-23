package com.example.back.servlets;

import com.example.back.dao.AccountDAO;
import com.example.back.dto.AccountDepositDTO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/account/deposit")
public class DepositAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AccountDAO accountDAO = new AccountDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        AccountDepositDTO depositRequest = gson.fromJson(reader, AccountDepositDTO.class);

        boolean success = accountDAO.deposit(depositRequest.getNumber(), depositRequest.getSum());

        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Deposit successful");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to deposit funds");
        }
    }
}
