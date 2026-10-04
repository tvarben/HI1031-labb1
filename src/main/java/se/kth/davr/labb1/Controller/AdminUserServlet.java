package se.kth.davr.labb1.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.kth.davr.labb1.Model.Enteties.User;
import se.kth.davr.labb1.Model.Exceptions.UpdateException;

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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User admin = (User) request.getSession(false).getAttribute("loggedInUser");
        try{
            int userId = Integer.parseInt(request.getParameter("userId"));
            String role = request.getParameter("role");

            userService.changeUserRole(admin.getId(), userId, role);

            response.sendRedirect(request.getContextPath() + "/admin/users?updated=true");

        } catch(IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", e.getMessage());
            doGet(request, response);

        } catch(SecurityException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());

        } catch(SelectException | UpdateException e) {
            throw new ServletException("Kunde inte ändra användarens roll", e);
        }
    }
}