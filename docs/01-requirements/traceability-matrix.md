# Матрица трассировки требований

## Проект: Веб-приложение для идентификации растений

Матрица связывает функциональные требования (FR) с Use Case, компонентами архитектуры и тест-кейсами.

---

## Матрица трассировки

| Требование | Описание | Use Case | Компонент | Тест |
|---|---|---|---|---|
| **FR-01** | Регистрация пользователя | UC-01 (Регистрация) | `AuthController`, `UserService`, `UserRepository` | `UserServiceTest#testRegister` |
| **FR-02** | Авторизация пользователя | UC-02 (Вход в систему) | `AuthController`, `CustomUserDetailsService`, Spring Security | `UserServiceTest#testLogin` |
| **FR-03** | Загрузка фото растения | UC-03 (Идентификация) | `PlantController.identify()`, Fetch API | `PlantServiceTest#testIdentify` |
| **FR-04** | Идентификация через Plant.id API | UC-03 (Идентификация) | `PlantService.identifyPlantAndSave()` | `PlantServiceIdentifyTest` |
| **FR-05** | Получение описания из Wikipedia | UC-03 (Идентификация) | `WikipediaService.getDescription()` | `PlantServiceTest#testWikipedia` |
| **FR-06** | Сохранение результата в БД | UC-03 (Идентификация) | `IdentificationRepository`, `PlantRepository` | `IdentificationRepositoryTest` |
| **FR-07** | Просмотр истории определений | UC-04 (История) | `PlantController.history()`, `IdentificationRepository` | `IdentificationRepositoryTest#testFindByUser` |
| **FR-08** | Просмотр карточки растения | UC-05 (Карточка растения) | `PlantController.plantCard()`, `PlantRepository` | `PlantRepositoryTest#testFindById` |
| **FR-09** | Выход из системы (logout) | UC-06 (Выход) | Spring Security logout filter | — |
| **FR-10** | REST API для растений | — | `PlantRestController`, `PlantService` | `PlantRepositoryTest` |
| **FR-11** | REST API для идентификаций | — | `IdentificationRestController` | `IdentificationRepositoryTest` |
| **FR-12** | REST API для текущего пользователя | — | `UserRestController.me()` | `UserRepositoryTest` |

---

## Матрица нефункциональных требований

| Требование | Описание | Реализация | Проверка |
|---|---|---|---|
| **NFR-01** | Пароли хешируются BCrypt | `PasswordEncoder` bean в `SecurityConfig` | `UserServiceTest#testPasswordEncoding` |
| **NFR-02** | Защита от XSS | Thymeleaf auto-escape | Ручное тестирование |
| **NFR-03** | Защита от SQL-инъекций | JPA/Hibernate параметризованные запросы | Code review |
| **NFR-04** | Адаптивный интерфейс | Bootstrap 5 grid | Визуальный осмотр |
| **NFR-05** | Покрытие тестами > 40% | JaCoCo Maven Plugin | `mvn jacoco:report` |
| **NFR-06** | Документация API | SpringDoc OpenAPI / Swagger UI | `/swagger-ui/index.html` |

---

## Трассировка Use Case → Endpoint

| Use Case | HTTP Метод | URL | Контроллер |
|---|---|---|---|
| UC-01 Регистрация | GET/POST | `/register` | `AuthController` |
| UC-02 Вход | GET/POST | `/login` | Spring Security |
| UC-03 Идентификация | POST | `/identify` | `PlantController` |
| UC-04 История | GET | `/history` | `PlantController` |
| UC-05 Карточка растения | GET | `/plants/{id}` | `PlantController` |
| UC-06 Выход | POST | `/logout` | Spring Security |
| — | GET | `/api/plants` | `PlantRestController` |
| — | GET | `/api/identifications/user/{id}` | `IdentificationRestController` |
| — | GET | `/api/users/me` | `UserRestController` |

---

## Покрытие требований

| Категория | Всего требований | Покрыто тестами | % покрытия |
|---|---|---|---|
| Функциональные (FR) | 12 | 10 | 83% |
| Нефункциональные (NFR) | 6 | 4 | 67% |
| **Итого** | **18** | **14** | **78%** |
