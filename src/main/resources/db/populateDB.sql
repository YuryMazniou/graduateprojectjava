DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM RESTAURANTS;
DELETE FROM DISHES;
DELETE FROM VOTES;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin1', 'admin1@gmail.com', 'admin1'),
  ('Admin2', 'admin2@gmail.com', 'admin2');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_ADMIN', 100002);

INSERT INTO restaurants (description,user_id) VALUES
   ('Garage',100001),
   ('PizzaMania',100002);

INSERT INTO dishes  (time_create_dish,description,price,restaurant_id) VALUES
    ('2019-07-03 8:10:00','stake','5.50',100003),
    ('2019-07-03 8:20:00','vegetables','4.5',100003),
    ('2019-07-03 8:25:00','wine','9.51',100003),
    ('2019-07-03 8:05:00','chicken','5.15',100004),
    ('2019-07-03 8:09:00','fruit','4.15',100004),
    ('2019-07-03 8:14:00','milk','2.15',100004);

INSERT INTO votes  (user_id,restaurant_id,time_create_vote) VALUES
     (100000,100003,'2019-07-03 10:14:00')
