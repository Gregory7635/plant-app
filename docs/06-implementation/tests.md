# Тестирование

## Стратегия тестирования

Тесты покрывают все три нижних слоя PCMEF: Mediator, Entity и Foundation.

## Структура тестов

```
src/test/java/com/example/plantapp/
├── control/
│   └── (интеграционные тесты контроллеров)
├── mediator/
│   ├── PlantServiceTest.java
│   ├── PlantServiceIdentifyTest.java
│   └── UserServiceTest.java
└── foundation/
    ├── PlantRepositoryTest.java
    ├── UserRepositoryTest.java
    └── IdentificationRepositoryTest.java
```

## Инструменты

| Инструмент | Назначение |
|---|---|
| **JUnit 5** | Фреймворк для написания тестов |
| **Spring Boot Test** | Загрузка контекста Spring для интеграционных тестов |
| **DataJpaTest** | Тестирование репозиториев с in-memory базой |
| **Mockito** | Мокирование зависимостей в unit-тестах |
| **JaCoCo** | Измерение покрытия кода |

## Запуск и отчёт

```bash
# Запуск тестов
mvn test

# Генерация отчёта JaCoCo
mvn jacoco:report

# Отчёт доступен по адресу:
# target/site/jacoco/index.html
```

## Покрытие

Целевое покрытие: **> 40%** согласно требованиям ТЗ.
Отчёт JaCoCo генерируется автоматически при сборке.
