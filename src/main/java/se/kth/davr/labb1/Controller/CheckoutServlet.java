package se.kth.davr.labb1.Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.davr.labb1.Model.Enteties.Cart;
import se.kth.davr.labb1.Model.Enteties.User;
import se.kth.davr.labb1.Model.Exceptions.InsertException;
import se.kth.davr.labb1.Model.Exceptions.InsufficientStockException;
import se.kth.davr.labb1.Model.Services.OrderService;

import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init(){this.orderService = new OrderService();}

    //if user tries to betala they fail then forward to cart.jsp and display error in red text
    //if user tried to betal and succeeds then go to checkout.jsp. if they click Bekräfta order, send request to this doPost
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItemCount() == 0) {
            request.setAttribute("error", "Varukorgen är tom.");
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    //check if shoppingCart == null or empty then forward back to cart and display error.
    //check if order is in stock. if not forward to cart.jsp and display error
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("loggedInUser");
        Cart shoppingCart = (Cart) session.getAttribute("cart");

        if (shoppingCart == null || shoppingCart.getItemCount() == 0) {
            request.setAttribute("error", "Varukorgen är tom.");
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
            return;
        }

        try {
            int orderId = orderService.confirmOrder(shoppingCart, user);
            session.removeAttribute("cart");
            response.sendRedirect(request.getContextPath() + "/order-confirmation?id=" + orderId);
        } catch (InsufficientStockException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
        } catch (InsertException e) {
            throw new ServletException("Could not place order", e);
        }
    }
}
