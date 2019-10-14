package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

public class MealRestController {

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

    private MealService service;
    private Integer userId = SecurityUtil.getAuthUserId();

//    public Meal create(Meal meal) {
//        return service.create(new Meal(SecurityUtil.getAuthUserId(), id.isEmpty() ? null : Integer.valueOf(id),
//                LocalDateTime.parse(request.getParameter("dateTime")),
//                request.getParameter("description"),
//                Integer.parseInt(request.getParameter("calories"))));
//    }
//
//    public boolean delete(int id) throws NotFoundException {//todo проверка на not found, =>> исключение
//        if (checkUserId(userId, id)) {
//            checkNotFoundWithId(service.delete(id), id);
//            return true;
//        }
//        else throw new NotFoundException("");
//    }
//
//    public Meal get(int id) throws NotFoundException {
//        if (checkUserId(userId, id)) {
//            return checkNotFoundWithId(service.get(id), id);
//        }
//        else throw new NotFoundException("");
//    }
//
//    public List<Meal> getAll() {
//        return service.getAll();
//    }
//
//    public Meal update(Meal meal) throws NotFoundException {
//        if (checkUserId(userId, meal.getId())) {
//            return checkNotFoundWithId(service.create(meal), meal.getId());
//        }
//        else throw new NotFoundException("");
//    }



}