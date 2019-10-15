package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeData;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.List;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(int autUserId, Meal meal) {
        meal.setUserId(SecurityUtil.getAuthUserId());
        return service.create(autUserId, meal);
    }

    public boolean delete(int autUserId, int id) {
           return service.delete(autUserId, id);
    }

    public Meal get(int autUserId, int id) {
        log.debug("in get()");
            return service.get(autUserId, id);
    }

    // отдает всю еду всех пользователей
    public List<MealTo> getAll(int caloriesPerDay) {
        return service.getAll(caloriesPerDay);
    }

    public Collection<MealTo> getWithFilter(int userId, DateTimeData dateTimeData, int caloriesPerDay){
        return service.getWithFilter(userId, dateTimeData, caloriesPerDay);
    }

}
//todo
// Реализация 'MealRestController' должен уметь обрабатывать запросы:
//    5.1: Отдать свою еду (для отображения в таблице, формат List<MealTo>), запрос БЕЗ параметров
//    5.2: Отдать свою еду, отфильтрованную по startDate, startTime, endDate, endTime
//    5.3: Отдать/удалить свою еду по id, параметр запроса - id еды. Если еда с этим id чужая или отсутствует - NotFoundException
//    5.4: Сохранить/обновить еду, параметр запроса - Meal. Если обновляемая еда с этим id чужая или отсутствует - NotFoundException
//    5.5: Сервлет мы удалим, а контроллер останется, поэтому возвращать List<MealTo> надо из контроллера.
//          И userId принимать в контроллере НЕЛЬЗЯ (иначе - для чего аторизация?).
//    5.6: В REST при update принято передавать id (см. AdminRestController.update)
//    5.7: Сделайте отдельный getAll без применения фильтра
// 6: Проверьте корректную обработку пустых значений date и time в контроллере