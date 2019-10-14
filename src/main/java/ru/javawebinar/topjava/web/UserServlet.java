package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.web.SecurityUtil.getAuthUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.setAuthUserId;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        String action = request.getParameter("action");
        log.debug("parametr action = " + request.getParameter("action"));
        switch (action == null ? "all" : action) {
            case ("setuser"):
                setAuthUserId(Integer.parseInt(request.getParameter("user")));
                log.debug("set authUserId = " + getAuthUserId());

//                request.setAttribute("meals",
//                        mealService.getWithFilter(userId, LocalDate.MIN, LocalDate.MAX, 2000));


               response.sendRedirect("meals");

             // request.getRequestDispatcher("/meals.jsp?action=filter").forward(request, response);
                break;
            case ("all"):

                request.getRequestDispatcher("/users.jsp").forward(request, response);
        }

        //request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
