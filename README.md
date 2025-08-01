## Процедура запуска автотестов

### Необходимые инструменты:
- IntelliJ IDEA
- Java Development Kit (JDK) 11
- Docker Desktop
- Google Chrome
- GIT

### Последовательность действий:
1.	Копировать проект на свою локальную машину
2.	Открыть проект в IntelliJ IDEA
3.	Запустить Docker Desktop
4.	В терминале запустить контейнеры с MySQL, PostgreSQL, эмулятором банковского сервиса и Jar-файлом командой **docker-compose up**
5.	Запустить автотесты командой **./gradlew clean test**
6.	Получить отчет Allure командой **./gradlew allureServe**
