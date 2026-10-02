package se.kth.davr.labb1.Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.davr.labb1.Model.Enteties.Cart;

import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    //use OrderService

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{


    }



}
