# 09 — API документация

## Swagger UI

После запуска приложения документация доступна по адресу:

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Эндпоинты

| Метод | URL | Описание |
|---|---|---|
| POST | /identify | Идентификация растения |
| GET | /history | История пользователя |
| GET | /plants/{id} | Карточка растения |
| GET | /api/plants | Все растения (JSON) |
| GET | /api/plants/{id} | Растение по ID (JSON) |
| GET | /api/identifications | Все идентификации (JSON) |
| GET | /api/identifications/user/{id} | Идентификации пользователя |
| GET | /api/users/me | Текущий пользователь |
