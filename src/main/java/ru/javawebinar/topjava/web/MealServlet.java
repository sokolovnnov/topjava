package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealService mealService = new MealService(new InMemoryMealRepository());
    //private MealRepository repository;
    int userId = SecurityUtil.getAuthUserId();
//    public static int getUserId() {
//        return userId;
//    }
    // @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        repository = new InMemoryMealRepository();
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug(" in doPost");
        userId = SecurityUtil.getAuthUserId();
        String id = request.getParameter("id");

        Meal meal = new Meal(userId, id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealService.create(meal);
        //repository.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        userId = SecurityUtil.getAuthUserId();
//        LocalDate sd;
//        if (request.getParameter("startdate").equals("")) sd = LocalDate.MIN;
//        else sd = LocalDate.parse(request.getParameter("startdate"));
//        LocalDate ed;
//        if (request.getParameter("enddate").equals("")) ed = LocalDate.MAX;
//        else ed = LocalDate.parse(request.getParameter("enddate"));
        log.debug("in doGet");
        log.debug("parametr action in mealServlet = " + request.getParameter("action"));
        switch (action == null ? "all" : action) {
//            case "setuser":
//                userId = Integer.parseInt(request.getParameter("user"));
//                log.debug("userId = " + userId);
//                //request.setAttribute("user", userId);
//                request.setAttribute("meals",
//                        mealService.getWithFilter(userId, LocalDate.MIN, LocalDate.MAX, 2000));
//                request.getRequestDispatcher("/meals.jsp").forward(request, response);
//                break;
            case "datefilter":
                log.info("Filter {}");
                log.info("startdate = " + request.getParameter("startdate"));
                log.info("endtdate = " + request.getParameter("enddate"));
                LocalDate sd;
                if (request.getParameter("startdate").equals("")) sd = LocalDate.MIN;
                else sd = LocalDate.parse(request.getParameter("startdate"));
                LocalDate ed;
                if (request.getParameter("enddate").equals("")) ed = LocalDate.MAX;
                else ed = LocalDate.parse(request.getParameter("enddate"));
                log.debug("userId in servlet before filter: " +userId);
                request.setAttribute("meals",
                        mealService.getWithFilter(userId, sd, ed, 2000));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
//            case "timefilter":
//                log.info("Filter {}");
//                log.info("startdate = " + request.getParameter("startdate"));
//                log.info("endtdate = " + request.getParameter("enddate"));
//                LocalDate sd;
//                if (request.getParameter("startdate").equals("")) sd = LocalDate.MIN;
//                else sd = LocalDate.parse(request.getParameter("startdate"));
//                LocalDate ed;
//                if (request.getParameter("enddate").equals("")) ed = LocalDate.MAX;
//                else ed = LocalDate.parse(request.getParameter("enddate"));
//                log.debug("userId in servlet before filter: " +userId);
//                request.setAttribute("meals",
//                        mealService.getWithFilter(userId, sd, ed, 2000));
//                request.getRequestDispatcher("/meals.jsp").forward(request, response);
//                break;
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                log.debug("userId in del = " + userId + "id: "+ id);
                mealService.delete(userId, id); //
                //repository.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0) :
                        mealService.get(userId, getId(request));//
                log.debug("meal = " + meal);
                log.debug("userId in create = " + userId);
                //repository.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":

//                LocalDate sd1;
//                if (request.getParameter("startdate").equals("") | request.getParameter("startdate") == null ) sd1 = LocalDate.MIN;
//                else sd1 = LocalDate.parse(request.getParameter("startdate"));
//                LocalDate ed1;
//                if (request.getParameter("enddate").equals("")) ed1 = LocalDate.MAX;
//                else ed1 = LocalDate.parse(request.getParameter("enddate"));

                request.setAttribute("meals",
                        mealService.getWithFilter(userId, LocalDate.MIN, LocalDate.MAX, 2000));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);

//                log.info("getAll");
//                request.setAttribute("meals",
//                        mealService.getAll(2000));//
//                //MealsUtil.getTos(repository.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
//                request.getRequestDispatcher("/meals.jsp").forward(request, response);
//                break;
            default:
//                LocalDate sd1;
//                if (request.getParameter("startdate").equals("")) sd1 = LocalDate.MIN;
//                else sd1 = LocalDate.parse(request.getParameter("startdate"));
//                LocalDate ed1;
//                if (request.getParameter("enddate").equals("")) ed1 = LocalDate.MAX;
//                else ed1 = LocalDate.parse(request.getParameter("enddate"));
//
//                request.setAttribute("meals",
//                        mealService.getWithFilter(userId, sd1, ed1, 2000));
//                request.getRequestDispatcher("/meals.jsp").forward(request, response);

        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        log.debug("id from reuest = " + paramId);
        return Integer.parseInt(paramId);
    }
}
