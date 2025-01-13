package com.ejb;

import com.microsoft.aad.msal4j.IAuthenticationResult;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet("/login/oauth2/code/")
public class AuthCallbackServlet extends HttpServlet {
    @EJB
    private AuthenticationService authService;

    @EJB
    private UserSessionBean userSession;

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String state = request.getParameter("state");
            String savedState = (String) request.getSession().getAttribute("state");

            if (!state.equals(savedState)) {
                throw new ServletException("Invalid state parameter");
            }

            String code = request.getParameter("code");
            IAuthenticationResult result = authService.getTokenByAuthCode(code);

            userSession.setTokens(result);
            request.getSession().removeAttribute("state");

            String userInfo = userService.getUserInfo(result.accessToken());

            request.getSession().setAttribute("remote user", "admin");

            request.getSession().setAttribute("User Info", userInfo);

            response.sendRedirect(request.getContextPath() + "/index.jsp");


        } catch (Exception e) {
            throw new ServletException("Authentication failed", e);
        }
    }
}
