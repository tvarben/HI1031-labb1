package se.kth.davr.labb1.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import se.kth.davr.labb1.Model.Exceptions.SelectException;
import se.kth.davr.labb1.Model.Services.UserService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        try{
            userService = new UserService();
        } catch (SQLException e) {
            throw new ServletException("Kunde inte starta användaradministrationen", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            request.setAttribute("users", userService.getAllUsers());
            request.setAttribute("title", "Användaradministration");
            request.getRequestDispatcher("/WEB-INF/views/admin/users.jsp").forward(request, response);
        } catch (SelectException e) {
            throw new ServletException("Kunde inte visa användarna", e);
        }
    }
}