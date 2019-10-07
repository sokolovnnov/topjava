package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.MealDataArray;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealDataArray mealDataArrayList = new MealDataArray();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");

        String action = req.getParameter("button");
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("datetime"));
        int calories = Integer.parseInt(req.getParameter("calories"));
        String description = req.getParameter("description");

        if (action.equals("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            mealDataArrayList.delete(id);
            resp.sendRedirect(req.getContextPath() + "/meals");
        }

        if (action.equals("edit")){
            int id = Integer.parseInt(req.getParameter("id"));
            mealDataArrayList.update(id, localDateTime, description, calories);
            resp.sendRedirect(req.getContextPath() + "/meals");
        }

        if (action.equals("new")) {
            Meal nMeal = mealDataArrayList.create(localDateTime,description,calories);
            mealDataArrayList.addToMemory(nMeal);
            resp.sendRedirect(req.getContextPath() + "/meals");

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meal3");

        List<MealTo> mealList = MealsUtil.getFiltered(mealDataArrayList.meals, LocalTime.MIN,
                LocalTime.MAX, MealsUtil.getDefaultCaloriesPerDay());

        request.setAttribute("mealList", mealList );
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
        //response.sendRedirect("meals.jsp");
    }
}
