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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    MealDataArryaList mealDataArryaList = new MealDataArryaList();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String action = req.getParameter("action") + "";
        if (action.equals("edit") || (action.equals("new"))) {
            String description = req.getParameter("description");
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("datetime"));
            int calories = Integer.parseInt(req.getParameter("calories"));

            MealService mealService = new MealService();
            switch (action) {
                case "edit":
                    long id = Long.parseLong(req.getParameter("id"));
                    mealService.save(id, dateTime, description, calories);
                    break;

                case "new":
                    mealService.create(dateTime, description, calories);
                    break;
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meal");

        String action = request.getParameter("action");

        switch (action) {
            case "edit":
                long editId = Long.parseLong(request.getParameter("id"));
                MealTo mealTo = MealsUtil.createTo(mealService.findById(editId), false);
                request.setAttribute("meal", mealTo);
            case "new":
                request.setAttribute("action", action);
                request.getRequestDispatcher("meal.jsp").forward(request, response);
                return;

            case "delete":
                mealService.delete(Long.parseLong(request.getParameter("id")));
                response.sendRedirect(request.getContextPath() + "/meals");
                return;
        }

        List<MealTo> mealList = MealsUtil.getFiltered(mealDataArryaList.meals, LocalTime.MIN, LocalTime.MAX, MealsUtil.getDefaultCaloriesPerDay());

        request.setAttribute("mealList", mealList );
        request.getRequestDispatcher("/meal.jsp").forward(request, response);
        //response.sendRedirect("meal.jsp");
    }
}
