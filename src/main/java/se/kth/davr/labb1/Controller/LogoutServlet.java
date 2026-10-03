package se.kth.davr.labb1.Controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "logoutServlet", value = "/logout")

public class LogoutServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //false gör att vi returnerar null om sessionen inte finns
        if(session != null) {
            session.invalidate(); //Rensar session, alltså logged in user och varukorg
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}