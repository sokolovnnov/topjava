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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


//todo:     Покажи, пожалуйста, где это в коде))
//           - при forward  данные фильтра остаются (мы же forward делаем, а не redirect),
//           не надо эти данные повторно сеттить как аттрибут в request.setAttribute (см., как использовать param в jsp)

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

    private DateTimeData dateTimeData = new DateTimeData();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

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

        switch (action == null ? "all" : action) {
            case "filter":
                log.info("Filter {}");
//                log.info("startdate = " + request.getParameter("startdate"));
//                log.info("endtdate = " + request.getParameter("enddate"));
//                log.info("startime = " + request.getParameter("starttime"));
//                log.info("endttime = " + request.getParameter("endtime"));
                request.setAttribute("meals", controller.getWithFilter(getDateTimeForFilter(request)));
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
                request.setAttribute("meals", controller.getWithFilter(getDateTimeForFilter(request)));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            default:
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        log.debug("id from request = " + paramId);
        return Integer.parseInt(paramId);
    }



    private DateTimeData getDateTimeForFilter(HttpServletRequest request) {
        if (request.getParameter("startdate") == null || request.getParameter("startdate").equals(""))
            dateTimeData.setStartDate(LocalDate.MIN);
        else
            dateTimeData.setStartDate(LocalDate.parse(request.getParameter("startdate")));

        if (request.getParameter("enddate") == null || request.getParameter("enddate").equals(""))
            dateTimeData.setEndDate(LocalDate.MAX);
        else
            dateTimeData.setEndDate(LocalDate.parse(request.getParameter("enddate")));

        if (request.getParameter("starttime") == null || request.getParameter("starttime").equals(""))
            dateTimeData.setStartTime(LocalTime.MIN);
        else
            dateTimeData.setStartTime(LocalTime.parse(request.getParameter("starttime")));

        if (request.getParameter("endtime") == null || request.getParameter("endtime").equals(""))
            dateTimeData.setEndTime(LocalTime.MAX);
        else
            dateTimeData.setEndTime(LocalTime.parse(request.getParameter("endtime")));

        return dateTimeData;
    }
}
