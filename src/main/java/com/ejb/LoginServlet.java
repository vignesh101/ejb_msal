package com.ejb;


import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

    @EJB
    private AuthenticationService authService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String state = generateState();
            request.getSession().setAttribute("state", state);

            String loginUrl = authService.getLoginUrl(state);
            response.sendRedirect(loginUrl);
        } catch (Exception e) {
            throw new ServletException("Login failed", e);
        }
    }

    private String generateState() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}
