package se.kth.davr.labb1.Controller;

import se.kth.davr.labb1.Model.DAO.IUserDb;
import se.kth.davr.labb1.Model.DAO.UserDbImpl;
import se.kth.davr.labb1.Model.Enteties.User;
import se.kth.davr.labb1.Model.Exceptions.SelectException;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private IUserDb userDb;

    @Override
    public void init() throws ServletException {

        try {
            userDb = new UserDbImpl();

        } catch (SQLException e) {
            throw new ServletException(
                    "Kunde inte ansluta till databasen",
                    e
            );
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request
                .getRequestDispatcher("/WEB-INF/views/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {

            User user = userDb.findUserByUsername(username);

            if (user != null &&
                    user.getPassword().equals(password)) {

                HttpSession session = request.getSession();

                session.setAttribute("loggedInUser", user);

                response.sendRedirect(
                        request.getContextPath() + "/index.jsp"
                );

            } else {

                request.setAttribute(
                        "error",
                        "Fel användarnamn eller lösenord"
                );

                request
                        .getRequestDispatcher("/WEB-INF/views/login.jsp")
                        .forward(request, response);
            }

        } catch (SelectException e) {

            throw new ServletException(
                    "Databasfel vid inloggningen",
                    e
            );
        }
    }
}