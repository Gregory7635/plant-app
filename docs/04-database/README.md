# Этап 4 — База данных

## Содержание

- [ER-диаграмма](er-diagram.md)
- [DDL-скрипт](ddl.sql)

## Описание схемы БД

База данных PostgreSQL, нормализована до **3NF**.

### Таблицы

| Таблица | Назначение |
|---|---|
| `users` | Пользователи системы (логин, пароль, роль) |
| `plants` | Справочник растений (название, описание, ссылка на Wikipedia) |
| `identifications` | История запросов (связь пользователь ↔ растение, вероятность, дата) |

### Связи

- `identifications.user_id` → `users.id` (ManyToOne)
- `identifications.plant_id` → `plants.id` (ManyToOne)

### Индексы

- `users.username` — UNIQUE, индексируется для быстрого поиска при авторизации
- `identifications.user_id` — индекс для быстрой выборки истории пользователя
- `identifications.created_at` — индекс для сортировки по дате
