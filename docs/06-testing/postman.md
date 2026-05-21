# Тестирование REST API через Postman

## Проект: Веб-приложение для идентификации растений

---

## Настройка Postman

### Base URL

```
http://localhost:8080
```

### Аутентификация

REST-эндпоинты защищены Spring Security. Для тестирования используйте Basic Auth:

| Поле | Значение |
|---|---|
| Username | `admin` (или ваш логин) |
| Password | `admin` (или ваш пароль) |

В Postman: вкладка **Authorization** → тип **Basic Auth**.

---

## Коллекция запросов

### 1. Растения (Plants)

#### GET /api/plants — Получить все растения

```
GET http://localhost:8080/api/plants
Authorization: Basic YWRtaW46YWRtaW4=
```

**Ожидаемый ответ (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Monstera deliciosa",
    "description": "Монстера — тропическое растение...",
    "imageUrl": null
  }
]
```

---

#### GET /api/plants/{id} — Получить растение по ID

```
GET http://localhost:8080/api/plants/1
Authorization: Basic YWRtaW46YWRtaW4=
```

**Ожидаемый ответ (200 OK):**
```json
{
  "id": 1,
  "name": "Monstera deliciosa",
  "description": "Монстера — тропическое растение..."
}
```

**При отсутствии (404 Not Found):**
```json
{
  "error": "Plant not found"
}
```

---

### 2. Идентификации (Identifications)

#### GET /api/identifications — Все идентификации

```
GET http://localhost:8080/api/identifications
Authorization: Basic YWRtaW46YWRtaW4=
```

**Ожидаемый ответ (200 OK):**
```json
[
  {
    "id": 1,
    "plant": { "id": 1, "name": "Monstera deliciosa" },
    "user": { "id": 1, "username": "admin" },
    "identifiedAt": "2026-04-15T10:30:00"
  }
]
```

---

#### GET /api/identifications/user/{id} — Идентификации пользователя

```
GET http://localhost:8080/api/identifications/user/1
Authorization: Basic YWRtaW46YWRtaW4=
```

---

### 3. Пользователи (Users)

#### GET /api/users/me — Текущий пользователь

```
GET http://localhost:8080/api/users/me
Authorization: Basic YWRtaW46YWRtaW4=
```

**Ожидаемый ответ (200 OK):**
```json
{
  "id": 1,
  "username": "admin",
  "roles": ["ROLE_ADMIN"]
}
```

---

### 4. Веб-эндпоинты (через форму)

#### POST /identify — Идентификация растения

Эндпоинт принимает `multipart/form-data`:

```
POST http://localhost:8080/identify
Content-Type: multipart/form-data

Body:
  file: [выбрать изображение растения]
```

В Postman: Body → form-data → key: `file`, Type: `File`.

---

## Swagger UI (альтернатива Postman)

После запуска приложения полная интерактивная документация доступна по адресу:

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON-спецификация:
```
http://localhost:8080/v3/api-docs
```

---

## Сценарии тестирования

| # | Сценарий | Метод | URL | Ожидаемый код |
|---|---|---|---|---|
| 1 | Получить все растения | GET | `/api/plants` | 200 |
| 2 | Получить растение по ID | GET | `/api/plants/1` | 200 |
| 3 | Несуществующее растение | GET | `/api/plants/9999` | 404 |
| 4 | Все идентификации | GET | `/api/identifications` | 200 |
| 5 | Идентификации пользователя | GET | `/api/identifications/user/1` | 200 |
| 6 | Текущий пользователь | GET | `/api/users/me` | 200 |
| 7 | Запрос без авторизации | GET | `/api/plants` | 401 |
