# Руководство администратора

## Развёртывание

### Вариант 1 — Docker (рекомендуется)

```bash
# Клонировать репозиторий
git clone https://github.com/Gregory7635/plant-app.git
cd plant-app

# Запустить (PostgreSQL + приложение)
PLANT_ID_API_KEY=ваш_ключ docker compose up --build
```

Приложение доступно на `http://localhost:8080`

---

### Вариант 2 — Локально (Maven + PostgreSQL)

```bash
# 1. Создать базу данных
psql -U postgres -c "CREATE DATABASE plantdb;"
psql -U postgres -d plantdb -f docs/04-database/ddl.sql

# 2. Задать переменные окружения
export PLANT_ID_API_KEY=ваш_ключ_plant_id
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=postgres

# 3. Запустить
mvn spring-boot:run
```

---

### Вариант 3 — WAR на Tomcat

```bash
# Сборка WAR
mvn clean package

# Скопировать в Tomcat
cp target/plantapp-0.0.1-SNAPSHOT.war $TOMCAT_HOME/webapps/plantapp.war
```

---

## Получение API ключа Plant.id

1. Зарегистрируйтесь на [plant.id](https://plant.id)
2. Перейдите в личный кабинет → API keys
3. Скопируйте ключ
4. Передайте через переменную окружения `PLANT_ID_API_KEY`

## Управление пользователями

Администратор (`ROLE_ADMIN`) имеет доступ к REST API:

```bash
# Список всех пользователей
GET /api/users

# Список всех идентификаций
GET /api/identifications
```
