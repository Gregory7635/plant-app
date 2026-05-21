# 06 — Тестирование

## Содержание

- [Тест-план](test-plan.md)
- [Тестирование REST API через Postman](postman.md)
- Результаты JaCoCo (покрытие кода > 40%): `mvn jacoco:report` → `target/site/jacoco/index.html`

## Инструменты

| Инструмент | Назначение |
|---|---|
| JUnit 5 | Модульное и интеграционное тестирование |
| Mockito | Мок-объекты для изоляции зависимостей |
| Spring Boot Test / DataJpaTest | Тестирование слоёв в Spring-контексте |
| JaCoCo | Измерение покрытия кода |

## Запуск тестов

```bash
mvn test
mvn jacoco:report
# Отчёт: target/site/jacoco/index.html
```
