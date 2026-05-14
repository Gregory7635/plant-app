# Техническая спецификация

## Системные требования

| Компонент | Минимум |
|---|---|
| Java | 17+ |
| PostgreSQL | 14+ |
| Maven | 3.8+ |
| RAM | 512 MB |
| Docker | 20.10+ (опционально) |

## Конфигурация

Все настройки через переменные окружения:

| Переменная | Описание | По умолчанию |
|---|---|---|
| `PLANT_ID_API_KEY` | Ключ Plant.id API | `your-api-key-here` |
| `SPRING_DATASOURCE_URL` | URL подключения к БД | `jdbc:postgresql://localhost:5432/plantdb` |
| `SPRING_DATASOURCE_USERNAME` | Пользователь БД | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Пароль БД | `postgres` |

## API документация

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Ограничения

- Максимальный размер загружаемого файла: **10 MB**
- Поддерживаемые форматы: JPEG, PNG, WEBP
- Доступность Plant.id API: требуется активный ключ
