package com.example.back.servlets;

import com.example.back.dao.PaymentDAO;
import com.example.back.dto.PaymentDTO;
import com.example.back.dto.PaymentResponse;
import com.example.back.model.Payment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PaymentDAO paymentDAO = new PaymentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestData = request.getReader().lines().collect(Collectors.joining());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        PaymentDTO paymentDTO = gson.fromJson(requestData, PaymentDTO.class);

        if(paymentDTO == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Body is null");
            return;
        }

        boolean status = paymentDAO.makePayment(new Payment(paymentDTO.getFrom(), paymentDTO.getTo(), paymentDTO.getSum(), new Date()));

        response.setContentType("application/json");
        String json = gson.toJson(new PaymentResponse(status));
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
