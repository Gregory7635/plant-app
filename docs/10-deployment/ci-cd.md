# CI/CD — Непрерывная интеграция и доставка

## Проект: Веб-приложение для идентификации растений

---

## Обзор пайплайна

```
Push → GitHub Actions → Build → Test → Docker Build → Deploy
```

Каждый коммит в ветку `main` автоматически проходит через этапы сборки и тестирования.

---

## GitHub Actions Workflow

Файл конфигурации: `.github/workflows/ci.yml`

```yaml
name: CI Pipeline

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build-and-test:
    runs-on: ubuntu-latest

    services:
      postgres:
        image: postgres:15
        env:
          POSTGRES_DB: plantdb_test
          POSTGRES_USER: postgres
          POSTGRES_PASSWORD: postgres
        ports:
          - 5432:5432
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up Java 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
          cache: 'maven'

      - name: Build and test
        env:
          PLANT_ID_API_KEY: ${{ secrets.PLANT_ID_API_KEY }}
          SPRING_DATASOURCE_URL: jdbc:postgresql://localhost:5432/plantdb_test
          SPRING_DATASOURCE_USERNAME: postgres
          SPRING_DATASOURCE_PASSWORD: postgres
        run: mvn clean verify

      - name: Generate JaCoCo report
        run: mvn jacoco:report

      - name: Upload JaCoCo report
        uses: actions/upload-artifact@v4
        with:
          name: jacoco-report
          path: target/site/jacoco/
```

---

## Этапы пайплайна

### 1. Сборка (Build)

```bash
mvn clean package -DskipTests
```

- Компиляция Java-кода
- Генерация JAR-артефакта
- Проверка зависимостей

### 2. Тестирование (Test)

```bash
mvn test
mvn jacoco:report
```

- Запуск JUnit 5 тестов
- Генерация JaCoCo-отчёта
- Проверка покрытия > 40%

### 3. Docker Build

```bash
docker build -t plant-app:latest .
```

Dockerfile (multi-stage build):

```dockerfile
# Этап 1: Сборка
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Этап 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## Переменные окружения

| Переменная | Описание | Где задать |
|---|---|---|
| `PLANT_ID_API_KEY` | API ключ Plant.id | GitHub Secrets |
| `SPRING_DATASOURCE_URL` | URL PostgreSQL | GitHub Secrets / docker-compose.yml |
| `SPRING_DATASOURCE_USERNAME` | Пользователь БД | GitHub Secrets |
| `SPRING_DATASOURCE_PASSWORD` | Пароль БД | GitHub Secrets |

---

## Локальный запуск пайплайна

Для локальной проверки перед push:

```bash
# 1. Сборка и тесты
mvn clean verify

# 2. JaCoCo отчёт
mvn jacoco:report
# Открыть: target/site/jacoco/index.html

# 3. Docker сборка
docker compose up --build
```

---

## Статус пайплайна

GitHub Actions отображает статус в репозитории:
- ✅ Зелёный: все тесты прошли, сборка успешна
- ❌ Красный: ошибка компиляции или тест упал

Badge для README:
```markdown
![CI](https://github.com/Gregory7635/plant-app/actions/workflows/ci.yml/badge.svg)
```
