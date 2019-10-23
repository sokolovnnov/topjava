package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-jdbc.xml"
})

@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    MealService mealService;

    @Test(expected = NotFoundException.class)
    public void test_get_by_other_user() {
        mealService.get(MEAL1_USER1.getId(), 100000);
    }

    @Test(expected = NotFoundException.class)
    public void test_delete_by_other_user() {
        mealService.delete(MEAL1_USER0.getId(), 100001);
    }

    @Test(expected = NotFoundException.class)
    public void test_update_by_other_user(){
        mealService.update(MEAL2_USER0, 100001);
    }

    @Test
    public void getBetweenDates() {
    }

    @Test
    public void getAll() {
        List<Meal> all = mealService.getAll(100001);
        assertMatch(all, MEAL2_USER1,  MEAL3_USER1, MEAL1_USER1);
    }

    @Test
    public void update() {
    }

    @Test
    public void test_delete() {
        mealService.delete(MEAL1_USER1.getId(), 100001);
        List<Meal> all = mealService.getAll(100001);
        assertMatch(all, MEAL2_USER1,  MEAL3_USER1);
    }

    @Test
    public void create() {
        Meal meal = new Meal(LocalDateTime.of
                (2014, 10, 19, 10, 23, 54),
                "dinner cr", 201);
        mealService.create(meal, 100001);
        assertMatch(mealService.get(meal.getId(), 100001), meal);
    }
}
/*MEAL1_USER0,  MEAL2_USER0,  MEAL3_USER0,*/