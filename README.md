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

-----------------------------

###### curl samples (application deployed in application context `graduateprojectjava`).
> For windows use `Git Bash`
###### get All Users
`curl -s http://localhost:8080/graduateprojectjava/restaurants/admin/users --user admin1@gmail.com:admin1`
###### get Users 100001
`curl -s http://localhost:8080/graduateprojectjava/restaurants/admin/users/100001 --user admin1@gmail.com:admin1`
###### get History
`curl -s http://localhost:8080/graduateprojectjava/restaurants/profile/statistic/history --user user1@yandex.ru:password1`
###### validate with Error
`curl -s -X POST -d '{}' -H 'Content-Type: application/json' http://localhost:8080/graduateprojectjava/restaurants/admin/users --user admin1@gmail.com:admin1`
`curl -s -X PUT -d '{"dateTime":"2015-05-30T07:00"}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/profile/meals/100003 --user user1@yandex.ru:password1`
###### give vote
`curl -s -X PUT -d '{}' -H 'Content-Type: application/json' http://localhost:8080/graduateprojectjava/restaurants/profile/vote/1 --user user1@yandex.ru:password1`
