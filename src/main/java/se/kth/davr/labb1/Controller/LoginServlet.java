package se.kth.davr.labb1.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.davr.labb1.Model.Enteties.User;
import se.kth.davr.labb1.Model.Services.UserService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        try {
            userService = new UserService();
        } catch (SQLException e) {
            throw new ServletException("Could not initialize UserService", e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username == null || username.trim().isEmpty() || password == null || password.isEmpty()){
            request.setAttribute("error", "Ange användarnamn och lösenord.");
            doGet(request,response);
            return;
        }
        User user = userService.authenticate(username,password);
        if(user == null){
            request.setAttribute("error", "Fel användarnamn eller lösenord.");
            doGet(request,response);
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", user);
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request,response);
    }
}
