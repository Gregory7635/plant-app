# Структура кода

## Дерево пакетов

```
src/main/java/com/example/plantapp/
│
├── PlantAppApplication.java          # Точка входа Spring Boot
│
├── control/                          # Слой C (Control)
│   ├── AuthController.java           # Регистрация и вход
│   ├── PlantController.java          # Основные страницы (index, history, plant-card)
│   ├── PlantRestController.java      # REST API для растений
│   ├── IdentificationRestController.java  # REST API для истории
│   ├── UserRestController.java       # REST API для пользователей
│   ├── ApiIdentificationController.java   # Альтернативный API-контроллер
│   ├── SecurityConfig.java           # Конфигурация Spring Security
│   ├── GlobalExceptionHandler.java   # Глобальная обработка ошибок
│   └── dto/
│       └── PlantDto.java             # Data Transfer Object для Plant
│
├── mediator/                         # Слой M (Mediator)
│   ├── PlantService.java             # Идентификация растений, история
│   ├── UserService.java              # Регистрация, управление пользователями
│   ├── WikipediaService.java         # Получение описаний из Wikipedia API
│   ├── CustomUserDetailsService.java # Реализация UserDetailsService для Spring Security
│   └── AppConfig.java                # Конфигурация бинов (RestTemplate и др.)
│
├── entity/                           # Слой E (Entity)
│   ├── Plant.java                    # JPA-сущность: растение
│   ├── User.java                     # JPA-сущность: пользователь
│   └── Identification.java           # JPA-сущность: запись идентификации
│
└── foundation/                       # Слой F (Foundation)
    ├── PlantRepository.java          # CRUD + кастомные запросы для Plant
    ├── UserRepository.java           # CRUD + findByUsername для User
    └── IdentificationRepository.java # CRUD + история по пользователю
```

## Паттерны рефакторинга

### Data Mapper

JPA + Hibernate автоматически отделяют объектную модель от реляционной схемы.
`Plant.java` — бизнес-объект, таблица `plants` — схема БД. Маппинг через аннотации `@Entity`, `@Column`, `@ManyToOne`.

### Identity Map

Hibernate Session Cache гарантирует, что в рамках одной транзакции при повторном обращении к одной записи возвращается тот же Java-объект, а не новый экземпляр.

### Lazy Load

Связи `@ManyToOne` в `Identification` (к `User` и `Plant`) используют `FetchType.LAZY` по умолчанию — связанные объекты загружаются только при обращении.
