package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeData;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    private MealRestController controller;

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        if (!id.isEmpty()) controller.get(Integer.parseInt(id)).getUserId();

        Meal meal = new Meal(null, id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        controller.create(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        log.debug("in doGet");
        log.debug("parametr action in mealServlet = " + request.getParameter("action"));
        DateTimeData dateTimeData = new DateTimeData();
        switch (action == null ? "filter" : action) {
            case "filter":
                log.info("Filter {}");
//                log.info("startdate = " + request.getParameter("startdate"));
//                log.info("endtdate = " + request.getParameter("enddate"));
//                log.info("startime = " + request.getParameter("starttime"));
//                log.info("endttime = " + request.getParameter("endtime"));
                dateTimeData = controller.getDateTimeForFilter(request);
                request.setAttribute("meals", controller.getWithFilter(dateTimeData));
                request.setAttribute("startdate", dateTimeData.getStartDate());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id); //
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0) :
                        controller.get(getId(request));//
                log.debug("meal = " + meal);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
                request.setAttribute("meals", controller.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            default:
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        log.debug("id from request = " + paramId);
        return Integer.parseInt(paramId);
    }
}
