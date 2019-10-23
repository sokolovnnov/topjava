package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal MEAL1_USER0 = new Meal(100002, LocalDateTime.of
            (2004, 10, 19, 10, 23, 54), "dinner 1", 201);
    public static final Meal MEAL2_USER0 = new Meal(100003, LocalDateTime.of
            (2012, 10, 19, 10, 23, 11), "dinner 2", 202);
    public static final Meal MEAL3_USER0 = new Meal(100004, LocalDateTime.of
            (2004, 10, 19, 10, 23, 53), "dinner 3", 203);

    public static final Meal MEAL1_USER1 = new Meal(100005, LocalDateTime.of
            (2004, 10, 19, 10, 23, 12), "breakfast 1", 301);
    public static final Meal MEAL2_USER1 = new Meal(100006, LocalDateTime.of
            (2004, 10, 19, 10, 23, 44), "breakfast 2", 302);
    public static final Meal MEAL3_USER1 = new Meal(100007, LocalDateTime.of
            (2004, 10, 19, 10, 23, 33), "breakfast 3", 303);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime", "description");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dateTime",  "description").isEqualTo(expected);
    }

}
