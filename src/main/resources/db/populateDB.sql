DELETE FROM USER_ROLES;
DELETE FROM USERS;
DELETE FROM RESTAURANTS;
DELETE FROM DISHES;
DELETE FROM VOTES;
DELETE FROM HISTORY_RESTAURANT_OBJECT;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User1', 'user1@yandex.ru', 'password1'),
  ('User2', 'user2@yandex.ru', 'password2'),
  ('Admin1', 'admin1@gmail.com', 'admin1'),
  ('Admin2', 'admin2@gmail.com', 'admin2');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_ADMIN', 100002),
  ('ROLE_ADMIN', 100003),
  ('ROLE_USER', 100002),
  ('ROLE_USER', 100003);

INSERT INTO restaurants (description,user_id) VALUES
   ('Garage',100002),
   ('PizzaMania',100003);

INSERT INTO dishes  (time_create_dish,description,price,restaurant_id,user_id) VALUES
('2019-07-03','stake','5.50',100004,100002),
('2019-07-03','vegetables','4.5',100004,100002),
('2019-07-03','wine','9.51',100004,100002),
('2019-07-03','chicken','5.15',100005,100003),
('2019-07-03','fruit','4.15',100005,100003),
('2019-07-03','milk','2.15',100005,100003);


INSERT INTO votes  (user_id,restaurant_id,time_create_vote) VALUES
     (100000,100004,'2019-07-03');

INSERT INTO history_restaurant_object (counts,description,votes_date) VALUES
(12345,'Garage','2019-07-02'),
(123456,'PizzaMania','2019-07-02'),
(1234,'Garage','2019-07-01'),
(123,'PizzaMania','2019-07-01')
