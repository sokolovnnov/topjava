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
//    public static final Meal MEAL4_USER0 = new Meal(100000, LocalDateTime.of
//            (2004, 10, 19, 10, 23, 51), "dinner 4", 204);
//    public static final Meal MEAL5_USER0 = new Meal(100000, LocalDateTime.of
//            (2004, 10, 19, 10, 23, 57), "dinner 5", 205);
//    public static final Meal MEAL6_USER0 = new Meal(100000, LocalDateTime.of
//            (2004, 10, 19, 10, 23, 50), "dinner 6", 206);
    public static final Meal MEAL1_USER1 = new Meal(100005, LocalDateTime.of
            (2004, 10, 19, 10, 23, 12), "breakfast 1", 301);
    public static final Meal MEAL2_USER1 = new Meal(100006, LocalDateTime.of
            (2004, 10, 19, 10, 23, 44), "breakfast 2", 302);
    public static final Meal MEAL3_USER1 = new Meal(100007, LocalDateTime.of
            (2004, 10, 19, 10, 23, 33), "breakfast 3", 303);
//    public static final Meal MEAL4_USER1 = new Meal(100000, LocalDateTime.of
//            (2004, 10, 19, 10, 23, 22), "breakfast 4", 304);
//    public static final Meal MEAL5_USER1 = new Meal(100000, LocalDateTime.of
//            (2004, 10, 19, 10, 23, 11), "breakfast 5", 305);
//    public static final Meal MEAL6_USER1 = new Meal(100000, LocalDateTime.of
//            (2004, 10, 19, 10, 23, 00), "breakfast 6", 306);

    //    public static final Meal MEAL1_USER3 = new Meal(100000, LocalDateTime.of
//            (2004,10,19, 10,23,34), "breakfast 7", 307);
//           (100000, 'dinner 1', 201, '2004-10-19 10:23:54'),
//           (100000, 'dinner 2', 202, '2012-10-30 10:23:11'),
//           (100000, 'dinner 3', 203, '2004-10-19 10:23:53'),
//           (100000, 'dinner 4', 204, '2004-10-19 10:23:51'),
//           (100000, 'dinner 5', 205, '2004-10-19 10:23:57'),
//           (100000, 'dinner 6', 206, '2004-10-19 10:23:50'),
//        (100001, 'breakfast 1', 301, '2004-10-19 10:23:12'),
//        (100001, 'breakfast 2', 302, '2004-10-19 10:23:44'),
//        (100001, 'breakfast 3', 303, '2004-10-19 10:23:33'),
//        (100001, 'breakfast 4', 304, '2004-10-19 10:23:22'),
//        (100001, 'breakfast 5', 305, '2004-10-19 10:23:11'),
//        (100001, 'breakfast 6', 306, '2004-10-19 10:23:00'),
//        (100003, 'breakfast 7', 307, '2004-10-19 10:23:34');
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
