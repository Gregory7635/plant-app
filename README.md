Веб-приложение для идентификации растений

Автор: [Васильев Григорий Юрьевич]

Группа: [ПИЖ-б-о-23-1]

Траектория: [Web]

Дата начала: [03.02.2026]

Дата сдачи: [07.06.2026]

## О проекте

Веб-приложение для идентификации растений по фотографии с использованием Plant.id API.

Проект разработан на Java + Spring Boot в соответствии с архитектурой PCMEF.

Пользователь может:

* загружать фотографии растений;
* получать идентификацию растения;
* просматривать описание из Wikipedia;
* хранить историю определений;
* авторизовываться и регистрироваться;
* использовать REST API;
* запускать приложение через Docker.

---

# Технологический стек

| Компонент        | Технология                  |
| ---------------- | --------------------------- |
| Язык             | Java 17                     |
| Backend          | Spring Boot                 |
| Security         | Spring Security             |
| ORM              | Spring Data JPA / Hibernate |
| База данных      | PostgreSQL                  |
| Frontend         | Thymeleaf + Bootstrap       |
| REST API         | Spring Web MVC              |
| Документация API | Swagger / OpenAPI           |
| Тестирование     | JUnit 5 + JaCoCo            |
| Контейнеризация  | Docker + Docker Compose     |
| Сборка           | Maven                       |

---

# Архитектура проекта (PCMEF)

```text
Presentation → Control → Mediator → Entity → Foundation
```

## Слои

### Presentation

HTML-страницы, Thymeleaf шаблоны, Bootstrap UI.

### Control

Spring MVC controllers и REST controllers.

### Mediator

Бизнес-логика приложения.

### Entity

JPA сущности.

### Foundation

Repository слой.

---

# Основной функционал

## Пользователь

* регистрация;
* авторизация;
* logout;
* просмотр истории определений.

## Идентификация растений

* загрузка фотографии;
* отправка изображения в Plant.id API;
* получение результата;
* сохранение истории;
* получение описания из Wikipedia API.

## REST API

Реализованы REST endpoints:

| Метод  | Endpoint                       |
| ------ | ------------------------------ |
| GET    | /api/plants                    |
| GET    | /api/plants/{id}               |
| POST   | /api/plants                    |
| PUT    | /api/plants/{id}               |
| DELETE | /api/plants/{id}               |
| GET    | /api/identifications           |
| GET    | /api/identifications/user/{id} |
| GET    | /api/users/me                  |

---

# Безопасность

Используется Spring Security.

Реализовано:

* BCrypt password hashing;
* role-based authorization;
* защита маршрутов;
* защита от SQL Injection через JPA;
* валидация данных.

Роли:

* ROLE_USER
* ROLE_ADMIN

---

# AJAX

Для отправки формы определения используется Fetch API.

Особенности:

* без перезагрузки страницы;
* динамическое обновление результата;
* loader/spinner во время запроса.

---

# Swagger / OpenAPI

Swagger UI доступен по адресу:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Структура проекта

```text
src/main/java/com/example/plantapp
│
├── control
├── mediator
├── entity
├── foundation
└── config

src/main/resources
│
├── templates
└── application.properties
```

---

# Запуск проекта локально

## Требования

* Java 17+
* Maven
* PostgreSQL

---

## Настройка базы данных

Создать БД:

```sql
CREATE DATABASE plantdb;
```

Дамп готовой базы - plantdb.sql

---

## application.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/plantdb
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
```

---

## Сборка

```bash
mvn clean package
```

---

## Запуск

```bash
mvn spring-boot:run
```

или

```bash
java -jar target/plantapp-0.0.1-SNAPSHOT.jar
```

---

# Docker запуск

## Запуск приложения

```bash
docker compose up --build
```

---

## После запуска

Приложение:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Тестирование

Используются:

* JUnit 5;
* Spring Boot Test;
* DataJpaTest;
* JaCoCo.

---

## Запуск тестов

```bash
mvn test
```

---

## Отчёт покрытия

```text
target/site/jacoco/index.html
```

---

## Готовая сборка WAR

```text
plantapp.war
```

Для сборки в war необходимо добавить в pom.xml в основной блок <project>
```xml
<packaging>war</packaging>
```

После запустить сборку
```bash
mvn clean package
```

---

# Docker Compose

Сервисы:

* app — Spring Boot application;
* postgres — PostgreSQL database.

---

# Документация по этапам

## 00 — Бизнес-модель

| Документ | Ссылка |
|---|---|
| Контекстная диаграмма IDEF0 | [context-diagram.md](docs/00-project-charter/context-diagram.md) |
| BUC-диаграмма | [buc-diagram.md](docs/00-project-charter/buc-diagram.md) |
| SWOT-анализ | [swot.md](docs/00-project-charter/swot.md) |
| ROI — Анализ окупаемости | [roi.md](docs/00-project-charter/roi.md) |
| Глоссарий | [glossary.md](docs/00-project-charter/glossary.md) |

## 01 — Требования

| Документ | Ссылка |
|---|---|
| Use Case диаграмма | [use-case-diagram.md](docs/01-requirements/use-case-diagram.md) |
| Domain Model | [domain-model.md](docs/01-requirements/domain-model.md) |
| Спецификации Use Case | [use-case-specifications.md](docs/01-requirements/use-case-specifications.md) |
| Матрица трассировки требований | [traceability-matrix.md](docs/01-requirements/traceability-matrix.md) |

## 02 — Архитектура

| Документ | Ссылка |
|---|---|
| PCMEF-диаграмма | [pcmef-diagram.md](docs/02-architecture/pcmef-diagram.md) |
| ADR — Архитектурные решения | [adr.md](docs/02-architecture/adr.md) |
| Описание интерфейсов | [interfaces.md](docs/02-architecture/interfaces.md) |

## 03 — База данных

| Документ | Ссылка |
|---|---|
| ER-диаграмма | [er-diagram.md](docs/03-database/er-diagram.md) |
| DDL-скрипт | [ddl.sql](docs/03-database/ddl.sql) |
| ORM-маппинг (JPA / Hibernate) | [orm-mapping.md](docs/03-database/orm-mapping.md) |

## 04 — Детальное проектирование

| Документ | Ссылка |
|---|---|
| Sequence-диаграммы | [sequence-diagrams.md](docs/04-detailed-design/sequence-diagrams.md) |
| Спецификация методов | [method-specs.md](docs/04-detailed-design/method-specs.md) |

## 05 — Реализация

| Документ | Ссылка |
|---|---|
| Структура кода и слои | [code-structure.md](docs/05-implementation/code-structure.md) |

## 06 — Тестирование

| Документ | Ссылка |
|---|---|
| Тест-план | [test-plan.md](docs/06-testing/test-plan.md) |
| Тестирование REST API (Postman) | [postman.md](docs/06-testing/postman.md) |

## 07 — Рефакторинг

| Документ | Ссылка |
|---|---|
| Анализ «запахов кода» | [code-smells.md](docs/07-refactoring/code-smells.md) |
| Паттерны Data Mapper / Identity Map | [README.md](docs/07-refactoring/README.md) |

## 08 — Интерфейс

| Документ | Ссылка |
|---|---|
| Скриншоты интерфейса | [screenshots.md](docs/08-ui/screenshots.md) |

## 09 — API

| Документ | Ссылка |
|---|---|
| OpenAPI / Swagger | [README.md](docs/09-api/README.md) |

## 10 — Развёртывание

| Документ | Ссылка |
|---|---|
| Руководство администратора | [admin-guide.md](docs/10-deployment/admin-guide.md) |
| CI/CD пайплайн | [ci-cd.md](docs/10-deployment/ci-cd.md) |

## 11 — Руководство пользователя

| Документ | Ссылка |
|---|---|
| Руководство пользователя | [user-guide.md](docs/11-user-guide/user-guide.md) |

## 12 — Итоговый отчёт

| Документ | Ссылка |
|---|---|
| Техническая спецификация | [technical-specification.md](docs/12-final-report/technical-specification.md) |
| Пояснительная записка (.docx) | [Пояснительная записка.docx](docs/12-final-report/Пояснительная%20записка.docx) |
| Презентация (.pptx) | [Презентация к курсовому проекту.pptx](docs/12-final-report/Презентация%20к%20курсовому%20проекту.pptx) |

---

# Статистика разработки

![Commit Activity](docs/images/git-stats-activity.png)
![Punch Card](docs/images/git-stats-punchcard.png)

| Метрика | Значение |
|---|---|
| Общее количество коммитов | 32 |
| Начало разработки | 13 марта 2026 |
| Завершение | 20 мая 2026 |
| Период разработки | ~10 недель |

# Авторы
[Васильев Григорий] — разработчик, документация
Группа [ПИЖ-б-о-23-1], email: [gregory.vasiliev@gmail.com], GitHub: [Gregory7635]