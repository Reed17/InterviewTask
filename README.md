Технологии:
Java 8, Spring 5 (Boot 2, JPA, Security)
MySQL(or PostgreSQL)
Все запросы по REST API.Проект должен содержать следующие методы:
1. Создание пользовательского акаунта.
2. Аутентификацию для зарегистрированных пользователей используя JWT.
3. Постраничный вывод списка пользователей(только для аутентифицированных пользователей).
4. Редактирование и Удаление любого пользователя(только для аутентифицированных пользователей).
5. Каждый аккаунт имеет кошелек в определенной (одной) валюте. Реализовать возможность пополнения баланса кошелька, перевода платежа между аккаунтами с
с проверкой на валидность данных

How to Run Application
-----------------------
Open application.yml file inside task/src/main/resources/ directory.

Fill in params in accordance to you local DB, e.g:

    datasource:
        url: jdbc:mysql://<your-mysql-host-name>:3306/<your-db-name>
        username: root
        password: root
Run ApplicationRunner.java class 
