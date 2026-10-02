package se.kth.davr.labb1.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import se.kth.davr.labb1.Model.Exceptions.SelectException;
import se.kth.davr.labb1.Model.Services.ItemService;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/items")
public class ItemServlet extends HttpServlet {

    private ItemService itemService;

    @Override
    public void init() {
        itemService = new ItemService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("items", itemService.getAllItems());

            request.getRequestDispatcher("/WEB-INF/views/items.jsp")
                    .forward(request, response);

        } catch (SelectException e) {
            throw new ServletException("Kunde inte visa produkterna", e);
        }
    }
}