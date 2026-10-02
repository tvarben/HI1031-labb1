package se.kth.davr.labb1.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import se.kth.davr.labb1.Model.Enteties.Cart;
import se.kth.davr.labb1.Model.Exceptions.SelectException;
import se.kth.davr.labb1.Model.Services.CartService;

import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() {
        cartService = new CartService();
    }

    private Cart getCart(HttpSession session) {
        synchronized (session) {
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }

            return cart;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", getCart(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Cart cart = getCart(request.getSession());
            cartService.addItem(cart, itemId, quantity);

            response.sendRedirect(request.getContextPath() + "/cart");

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute(
                    "error",
                    "Kunde inte lägga till varan. Kontrollera produkt och antal."
            );
            doGet(request, response);

        } catch (SelectException e) {
            throw new ServletException("Databasfel när varan skulle hämtas", e);
        }
    }
}