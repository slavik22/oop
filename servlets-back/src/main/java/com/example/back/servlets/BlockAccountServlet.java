package com.example.back.servlets;

import com.example.back.dao.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/account/block")
public class BlockAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getAttribute("userId");
        String accountNumber = request.getParameter("accountNumber");

        boolean isOwner = accountDAO.isAccountOwner(userId, accountNumber);
        if (!isOwner) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("You are not authorized to block this account");
            return;
        }

        boolean success = accountDAO.blockAccount(accountNumber);
        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Account blocked successfully");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to block account");
        }
    }
}
