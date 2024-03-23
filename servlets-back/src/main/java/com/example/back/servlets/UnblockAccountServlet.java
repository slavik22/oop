package com.example.back.servlets;

import com.example.back.dao.AccountDAO;
import com.example.back.dao.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/account/unblock")
public class UnblockAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AccountDAO accountDAO = new AccountDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = (int) request.getAttribute("userId");

        if (!userDAO.isAdmin(userId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("You are not authorized to unblock accounts");
            return;
        }

        String accountNumber = request.getParameter("accountNumber");

        boolean success = accountDAO.unblockAccount(accountNumber);
        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Account unblocked successfully");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to unblock account");
        }
    }
}
