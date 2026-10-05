package se.kth.davr.labb1.Controller;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import se.kth.davr.labb1.Model.Enteties.Role;
import se.kth.davr.labb1.Model.Enteties.User;
import se.kth.davr.labb1.Model.Exceptions.SelectException;
import se.kth.davr.labb1.Model.Services.UserService;

import java.io.IOException;
import java.sql.SQLException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    private UserService userService;

    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig) throws ServletException {
        try{
            userService = new UserService();
        } catch (SQLException e) {
            throw new ServletException("Kunde inte starta behörighetskontrollen", e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); //Hämta befintlig session, skapa inte om den inte finns
        User sessionUser;
        if (session == null) {
            sessionUser = null;
        } else {
            sessionUser = (User) session.getAttribute("loggedInUser");
        }

        if(sessionUser == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        try{
            User currentUser = userService.getUserById(sessionUser.getId());
            if(currentUser == null) {
                session.invalidate();
                res.sendRedirect(req.getContextPath() + "/login"); //Läser rollen igen ifall den ändrat sedan sessionen skapades
                return;
            }
            session.setAttribute("loggedInUser", currentUser);
            if(currentUser.getRole() != Role.admin) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Du saknar behörighet till denna sida");
                return;
            }
            chain.doFilter(request, response); //Godkänt, gå vidare
        } catch(SelectException e) {
            throw new ServletException("Kunde inte kontrollera behörigheten", e);
        }
    }
}