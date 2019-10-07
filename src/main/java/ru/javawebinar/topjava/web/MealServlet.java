package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.MealDataArryaList;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meal");
        List<MealTo> mealList = MealsUtil.getFiltered(new MealDataArryaList().meals, LocalTime.MIN, LocalTime.MAX, MealsUtil.getDefaultCaloriesPerDay());

        request.setAttribute("mealList", mealList );
        request.getRequestDispatcher("/meal.jsp").forward(request, response);
        //response.sendRedirect("meal.jsp");
    }
}
