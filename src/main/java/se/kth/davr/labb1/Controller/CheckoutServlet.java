package se.kth.davr.labb1.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import se.kth.davr.labb1.Model.DTO.CartDTO;
import se.kth.davr.labb1.Model.Enteties.Cart;
import se.kth.davr.labb1.Model.Enteties.User;
import se.kth.davr.labb1.Model.Exceptions.InsertException;
import se.kth.davr.labb1.Model.Exceptions.InsufficientStockException;
import se.kth.davr.labb1.Model.Services.CartService;
import se.kth.davr.labb1.Model.Services.OrderService;

import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private OrderService orderService;
    private CartService cartService;

    @Override
    public void init() {
        orderService = new OrderService();
        cartService = new CartService();
    }

    private void prepareCartView(HttpServletRequest request, HttpSession session, Cart cart) {
        CartDTO cartDTO = cartService.getCartDTO(cart);
        request.setAttribute("cart", cartDTO);
        session.setAttribute("cartView", cartDTO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        prepareCartView(request, session, cart);

        if (cart == null || cart.getItemCount() == 0) {
            request.setAttribute("error", "Varukorgen är tom.");
            request.setAttribute("title", "Varukorg");
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
            return;
        }
        request.setAttribute("title", "Kassa");
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("loggedInUser");
        Cart shoppingCart = (Cart) session.getAttribute("cart");

        if (shoppingCart == null || shoppingCart.getItemCount() == 0) {
            prepareCartView(request, session, shoppingCart);

            request.setAttribute("error", "Varukorgen är tom.");
            request.setAttribute("title", "Varukorg");
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
            return;
        }

        try {
            int orderId = orderService.confirmOrder(shoppingCart, user);
            session.removeAttribute("cart");
            session.removeAttribute("cartView");
            response.sendRedirect(request.getContextPath() + "/order-confirmation?id=" + orderId);

        } catch (InsufficientStockException e) {
            prepareCartView(request, session, shoppingCart);
            request.setAttribute("error", e.getMessage());
            request.setAttribute("title", "Varukorg");
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
        } catch (InsertException e) {
            throw new ServletException("Kunde inte lägga ordern", e);
        }
    }
}