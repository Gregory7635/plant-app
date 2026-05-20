# 10 — Развёртывание

## Содержание

- [Руководство администратора](admin-guide.md)

## Быстрый старт (Docker)

```bash
git clone https://github.com/Gregory7635/plant-app.git
cd plant-app
PLANT_ID_API_KEY=ваш_ключ docker compose up --build
```

Приложение: `http://localhost:8080`

## Локально (Maven)

```bash
export PLANT_ID_API_KEY=ваш_ключ
mvn spring-boot:run
```
