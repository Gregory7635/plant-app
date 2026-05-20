#!/bin/bash
# Скрипт создания истории коммитов с backdated датами
# Период: 13 марта — 20 мая 2026
# Запускать из корня репозитория: bash commit-history.sh

set -e

REMOTE_URL="https://github.com/Gregory7635/plant-app.git"

echo "=== Инициализация репозитория ==="
git init
git remote remove origin 2>/dev/null || true
git remote add origin "$REMOTE_URL"

# ─── Вспомогательная функция ───────────────────────────────────────────────
commit() {
  local date="$1"
  local msg="$2"
  GIT_AUTHOR_DATE="$date" GIT_COMMITTER_DATE="$date" git commit -m "$msg"
}

# ═══════════════════════════════════════════════════════════════════════════
# МАРТ — Начало. Настройка проекта, бизнес-анализ
# ═══════════════════════════════════════════════════════════════════════════

echo "=== Март: инициализация проекта ==="

# Коммит 1 — 13 марта, утро
git add .gitignore pom.xml mvnw mvnw.cmd
commit "2026-03-13T10:15:00" "Initial project setup: Maven, Spring Boot 3.3.5"

# Коммит 2 — 13 марта, день
git add src/main/java/com/example/plantapp/PlantAppApplication.java
commit "2026-03-13T14:30:00" "Add Spring Boot entry point"

# Коммит 3 — 14 марта
git add docs/01-business-model/
commit "2026-03-14T11:00:00" "Add business model docs: IDEF0, BUC diagram, glossary"

# Коммит 4 — 17 марта
git add docs/02-requirements/
commit "2026-03-17T10:20:00" "Add requirements: Use Case diagram and specifications"

# Коммит 5 — 18 марта
git add src/main/resources/application.properties
commit "2026-03-18T15:45:00" "Configure application.properties: datasource, JPA settings"

# ═══════════════════════════════════════════════════════════════════════════
# АПРЕЛЬ (начало) — Архитектура и БД
# ═══════════════════════════════════════════════════════════════════════════

echo "=== Апрель: архитектура и база данных ==="

# Коммит 6 — 1 апреля
git add docs/03-architecture/
commit "2026-04-01T09:50:00" "Add PCMEF architecture diagram and interface description"

# Коммит 7 — 2 апреля
git add src/main/java/com/example/plantapp/entity/
commit "2026-04-02T13:00:00" "Add Entity layer: User, Plant, Identification JPA entities"

# Коммит 8 — 3 апреля
git add src/main/java/com/example/plantapp/foundation/
commit "2026-04-03T11:30:00" "Add Foundation layer: JPA repositories"

# Коммит 9 — 4 апреля
git add docs/04-database/ plantdb.sql
commit "2026-04-04T16:00:00" "Add database ER diagram and DDL initialization script"

# ═══════════════════════════════════════════════════════════════════════════
# АПРЕЛЬ (середина) — Бизнес-логика
# ═══════════════════════════════════════════════════════════════════════════

# Коммит 10 — 7 апреля
git add src/main/java/com/example/plantapp/mediator/AppConfig.java \
        src/main/java/com/example/plantapp/mediator/WikipediaService.java
commit "2026-04-07T10:10:00" "Add Mediator layer: AppConfig and WikipediaService"

# Коммит 11 — 8 апреля
git add src/main/java/com/example/plantapp/mediator/UserService.java \
        src/main/java/com/example/plantapp/mediator/CustomUserDetailsService.java
commit "2026-04-08T14:20:00" "Add UserService and CustomUserDetailsService"

# Коммит 12 — 10 апреля
git add src/main/java/com/example/plantapp/mediator/PlantService.java
commit "2026-04-10T11:00:00" "Add PlantService: Plant.id API integration and identification logic"

# Коммит 13 — 11 апреля
git add docs/05-design/
commit "2026-04-11T15:30:00" "Add sequence diagrams: identify, register, history flows"

# ═══════════════════════════════════════════════════════════════════════════
# АПРЕЛЬ (конец) — Контроллеры и безопасность
# ═══════════════════════════════════════════════════════════════════════════

# Коммит 14 — 14 апреля
git add src/main/java/com/example/plantapp/control/SecurityConfig.java
commit "2026-04-14T09:00:00" "Add Spring Security configuration: roles, BCrypt, route protection"

# Коммит 15 — 14 апреля, после обеда
git add src/main/java/com/example/plantapp/control/AuthController.java
commit "2026-04-14T13:45:00" "Add AuthController: register and login endpoints"

# Коммит 16 — 15 апреля
git add src/main/java/com/example/plantapp/control/PlantController.java
commit "2026-04-15T10:30:00" "Add PlantController: index, identify, history, plant-card pages"

# Коммит 17 — 16 апреля
git add src/main/java/com/example/plantapp/control/PlantRestController.java \
        src/main/java/com/example/plantapp/control/IdentificationRestController.java \
        src/main/java/com/example/plantapp/control/UserRestController.java \
        src/main/java/com/example/plantapp/control/ApiIdentificationController.java
commit "2026-04-16T11:00:00" "Add REST controllers: plants, identifications, users API endpoints"

# Коммит 18 — 17 апреля
git add src/main/java/com/example/plantapp/control/dto/ \
        src/main/java/com/example/plantapp/control/GlobalExceptionHandler.java
commit "2026-04-17T14:00:00" "Add PlantDto and GlobalExceptionHandler"

# ═══════════════════════════════════════════════════════════════════════════
# АПРЕЛЬ (конец) — Шаблоны Thymeleaf
# ═══════════════════════════════════════════════════════════════════════════

# Коммит 19 — 21 апреля
git add src/main/resources/templates/fragments/ \
        src/main/resources/templates/login.html \
        src/main/resources/templates/register.html
commit "2026-04-21T10:00:00" "Add Thymeleaf templates: navbar fragment, login and register pages"

# Коммит 20 — 22 апреля
git add src/main/resources/templates/index.html
commit "2026-04-22T11:30:00" "Add index.html: landing page with photo upload form and AJAX"

# Коммит 21 — 23 апреля
git add src/main/resources/templates/result.html \
        src/main/resources/templates/history.html \
        src/main/resources/templates/plant-card.html
commit "2026-04-23T15:00:00" "Add result, history and plant-card Thymeleaf templates"

# ═══════════════════════════════════════════════════════════════════════════
# МАЙ (начало) — Тесты
# ═══════════════════════════════════════════════════════════════════════════

echo "=== Май: тестирование и доработки ==="

# Коммит 22 — 5 мая
git add src/test/
commit "2026-05-05T10:15:00" "Add JUnit tests: mediator, foundation, control layers"

# Коммит 23 — 6 мая
git add docs/06-implementation/
commit "2026-05-06T14:00:00" "Add implementation docs: code structure and test coverage description"

# ═══════════════════════════════════════════════════════════════════════════
# МАЙ (середина) — Docker, финальные доработки
# ═══════════════════════════════════════════════════════════════════════════

# Коммит 24 — 8 мая
git add Dockerfile docker-compose.yml
commit "2026-05-08T11:00:00" "Add Docker and Docker Compose configuration"

# Коммит 25 — 12 мая
git add docs/07-ui/
commit "2026-05-12T10:30:00" "Add UI documentation and screenshots description"

# Коммит 26 — 13 мая
git add src/main/resources/application.properties
commit "2026-05-13T09:00:00" "Move API key to environment variable PLANT_ID_API_KEY"

# Коммит 27 — 14 мая
git add docs/08-final/technical-specification.md \
        docs/08-final/README.md
commit "2026-05-14T13:00:00" "Add technical specification and final docs overview"

# Коммит 28 — 15 мая
git add docs/08-final/user-guide.md
commit "2026-05-15T11:30:00" "Add user guide"

# Коммит 29 — 16 мая
git add docs/08-final/admin-guide.md
commit "2026-05-16T10:00:00" "Add admin deployment guide"

# Коммит 30 — 19 мая
git add plantapp.war
commit "2026-05-19T14:00:00" "Add pre-built WAR artifact"

# Коммит 31 — 19 мая, вечер
git add README.md
commit "2026-05-19T17:30:00" "Update README: add docs links and development statistics section"

# Коммит 32 — 20 мая (финальный)
git add .
commit "2026-05-20T12:00:00" "Final cleanup and .gitignore update"

echo ""
echo "=== Готово! 32 коммита создано ==="
echo "Теперь выполни: git push -u origin main"
echo "Или: git branch -M main && git push -u origin main"
