[![Codacy Badge](https://api.codacy.com/project/badge/Grade/63f08a19fc92430ab0f688804d4d47a6)](https://www.codacy.com/app/YuryMazniou/graduateprojectjava?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=YuryMazniou/graduateprojectjava&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.com/YuryMazniou/graduateprojectjava.svg?branch=master)](https://travis-ci.com/YuryMazniou/graduateprojectjava)

# Graduate Project

----
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository.

It should contain the code and **README.md with API documentation and curl commands to get data for voting and vote.**

----
Приложение для выяснения того "Какой самый лучший ресторан сегодня для обеда по версии пользователей?"
Каждый день до 9 утра админы имеют право заполнить меню ресторана актуальными на сегодня блюдами.Затем с 9-00 до 11-00
происходит голосование зарегистрированных пользователей.После этого в 11-00 становятся доступны результаты голосования и
собственно принятие решения куда идти покушать сегодня...

Если запустить приложение через cargo plugin работа приложения не будет зависить от времени и сегодняшная дата будет 
захардкожена 2019-07-03.
Если запустить через tomcat idea работа приложения будет как в описании...

##### curl commands (application deployed in application context `graduateprojectjava`).
> For windows use `Git Bash`
###### get All Users
`curl -s http://localhost:8080/graduateprojectjava/restaurants/admin/users --user admin1@gmail.com:admin1`
###### get Users 100001
`curl -s http://localhost:8080/graduateprojectjava/restaurants/admin/users/100001 --user admin1@gmail.com:admin1`
###### validate with Error
`curl -s -X POST -d '{}' -H 'Content-Type: application/json' http://localhost:8080/graduateprojectjava/restaurants/admin/users --user admin1@gmail.com:admin1`
#### Statistic for users and admins
###### get History
`curl -s http://localhost:8080/graduateprojectjava/restaurants/profile/statistic/history --user user1@yandex.ru:password1`
###### get Result of day after voting
`curl -s http://localhost:8080/graduateprojectjava/restaurants/profile/statistic/resultofday --user user1@yandex.ru:password1`
###### get List for voting
`curl -s http://localhost:8080/graduateprojectjava/restaurants/profile/statistic/listforvotes --user user1@yandex.ru:password1`
###### get History dishes for restaurant only by admin
`curl -s http://localhost:8080/graduateprojectjava/restaurants/profile/statistic/dish/100004 --user admin1@gmail.com:admin1`
#### CRUD Restaurant only for admins
###### create Restaurant
`curl -s -X POST -d '{"description":"New Restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduateprojectjava/restaurants/admin/restaurant --user admin1@gmail.com:admin1`
###### delete Restaurant
`curl -s -X DELETE http://localhost:8080/graduateprojectjava/restaurants/admin/restaurant/100023 --user admin1@gmail.com:admin1`
###### update Restaurant
`curl -s -X PUT -d '{"description":"Updated restaurant"}' -H 'Content-Type: application/json' http://localhost:8080/graduateprojectjava/restaurants/admin/restaurant/100004 --user admin1@gmail.com:admin1`
###### get user's Restaurants
`curl -s http://localhost:8080/graduateprojectjava/restaurants/admin/restaurant --user admin1@gmail.com:admin1`
#### CRUD Dish only for admins
###### create Dish
`curl -s -X POST -d '{"description":"New Dish","price":"10.1"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduateprojectjava/restaurants/admin/dish/100004 --user admin1@gmail.com:admin1`
###### delete Dish
`curl -s -X DELETE http://localhost:8080/graduateprojectjava/restaurants/admin/dish/100021 --user admin1@gmail.com:admin1`
###### update Dish
`curl -s -X PUT -d '{"description":"Updated dish","price":"10.9"}'  -H 'Content-Type: application/json' http://localhost:8080/graduateprojectjava/restaurants/admin/dish/100008?restaurant_id=100004 --user admin1@gmail.com:admin1`
###### get restaurant's Dish 
`curl -s http://localhost:8080/graduateprojectjava/restaurants/admin/dish/100004 --user admin1@gmail.com:admin1`
#### CRUD Vote for users and admins
###### create or update vote
`curl -s -X PUT -d '{}' -H 'Content-Type: application/json' http://localhost:8080/graduateprojectjava/restaurants/profile/vote/100005 --user user1@yandex.ru:password1`
###### get user's Vote
`curl -s  http://localhost:8080/graduateprojectjava/restaurants/profile/vote --user user1@yandex.ru:password1`
###### delete Vote
`curl -s -X DELETE http://localhost:8080/graduateprojectjava/restaurants/profile/vote/100014 --user user1@yandex.ru:password1`
