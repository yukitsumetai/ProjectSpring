package com.telekom;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HelloServelet extends HttpServlet {
    @EJB
    private EJBDemo ejbDemo;

    @EJB
    private Client client;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String message = ejbDemo.getMessage(client.getLogin());
        request.setAttribute("m", message);
        if (!message.equals("Пройдите регистрацию")) {
            request.setAttribute("register", false);
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        boolean registered = ejbDemo.login(login, password);

        if (!registered) {
            request.setAttribute("s", "Неправильные данные");
        } else request.setAttribute("register", false);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

}
