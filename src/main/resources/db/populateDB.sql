DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, description, calories, time_date) VALUES
(100000, 'dinner 1', 201, '2004-10-19 10:23:54'),
(100000, 'dinner 2', 202, '2012-10-30 10:23:11'),
(100000, 'dinner 3', 203, '2004-10-19 10:23:53'),
(100000, 'dinner 4', 204, '2004-10-19 10:23:51'),
(100000, 'dinner 5', 205, '2004-10-19 10:23:57'),
(100000, 'dinner 6', 206, '2004-10-19 10:23:50'),
(100001, 'breakfast 1', 300, '2004-10-19 10:23:12'),
(100001, 'breakfast 2', 301, '2004-10-19 10:23:44'),
(100001, 'breakfast 3', 301, '2004-10-19 10:23:33'),
(100001, 'breakfast 4', 301, '2004-10-19 10:23:22'),
(100001, 'breakfast 5', 301, '2004-10-19 10:23:11'),
(100001, 'breakfast 6', 301, '2004-10-19 10:23:00'),
(100001, 'breakfast 7', 301, '2004-10-19 10:23:34');


