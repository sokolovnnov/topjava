package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealMapDao;
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

    private MealMapDao mealMapDao = new MealMapDao();

    //todo switch!!

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");

        String action = req.getParameter("button");

        if (action.equals("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            mealMapDao.delete(mealMapDao.getById(id));
            resp.sendRedirect(req.getContextPath() + "/meals");
            return;
        }

        if (action.equals("edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Meal mealForUpd = mealMapDao.getById(id);
            req.setAttribute("mealForUpd", mealForUpd);
            req.setAttribute("action", action);
            req.getRequestDispatcher("/inputmeal.jsp").forward(req, resp);
            return;
        }

        if (action.equals("new")) {
            req.setAttribute("action", action);
            req.getRequestDispatcher("/inputmeal.jsp").forward(req, resp);
            return;
        }

        if (action.equals("save") & (!req.getParameter("id").equals(""))) {
            mealMapDao.update(new Meal(Integer.parseInt(req.getParameter("id")),
                    LocalDateTime.parse(req.getParameter("datetime")),
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories"))));
            resp.sendRedirect(req.getContextPath() + "/meals");
            return;
        }

        if (action.equals("save") & req.getParameter("id").equals("")) {
            {
                mealMapDao.create(new Meal(mealMapDao.mealDataArray.atomicCount.incrementAndGet(),
                        LocalDateTime.parse(req.getParameter("datetime")),
                        req.getParameter("description"),
                        Integer.parseInt(req.getParameter("calories"))));
                resp.sendRedirect(req.getContextPath() + "/meals");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meal");

        List<MealTo> mealList = MealsUtil.getFiltered(mealMapDao.getAll(), LocalTime.MIN,
                LocalTime.MAX, MealsUtil.getDefaultCaloriesPerDay());

        request.setAttribute("mealList", mealList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
