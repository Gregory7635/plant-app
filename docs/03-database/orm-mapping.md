# ORM-маппинг (JPA / Hibernate)

## Проект: Веб-приложение для идентификации растений

Маппинг JPA-сущностей на таблицы PostgreSQL через аннотации Hibernate.

---

## Сущность User → таблица `users`

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                          // PRIMARY KEY SERIAL

    @Column(unique = true, nullable = false)
    private String username;                  // VARCHAR UNIQUE NOT NULL

    @Column(nullable = false)
    private String password;                  // VARCHAR NOT NULL (BCrypt hash)

    @Column(nullable = false)
    private String role;                      // VARCHAR NOT NULL (ROLE_USER / ROLE_ADMIN)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Identification> identifications;
}
```

| Java-поле | Колонка БД | Тип SQL | Ограничения |
|---|---|---|---|
| `id` | `id` | `BIGSERIAL` | `PRIMARY KEY` |
| `username` | `username` | `VARCHAR(255)` | `UNIQUE NOT NULL` |
| `password` | `password` | `VARCHAR(255)` | `NOT NULL` |
| `role` | `role` | `VARCHAR(50)` | `NOT NULL` |

---

## Сущность Plant → таблица `plants`

```java
@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;                      // Научное название

    @Column(columnDefinition = "TEXT")
    private String description;              // Описание из Wikipedia

    @Column(name = "image_url")
    private String imageUrl;                 // URL изображения

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL)
    private List<Identification> identifications;
}
```

| Java-поле | Колонка БД | Тип SQL | Ограничения |
|---|---|---|---|
| `id` | `id` | `BIGSERIAL` | `PRIMARY KEY` |
| `name` | `name` | `VARCHAR(255)` | `NOT NULL` |
| `description` | `description` | `TEXT` | — |
| `imageUrl` | `image_url` | `VARCHAR(512)` | — |

---

## Сущность Identification → таблица `identifications`

```java
@Entity
@Table(name = "identifications")
public class Identification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;                       // FK → users.id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;                     // FK → plants.id

    @Column(name = "confidence")
    private Double confidence;              // Вероятность (0.0–1.0)

    @Column(name = "identified_at")
    private LocalDateTime identifiedAt;    // Дата и время запроса
}
```

| Java-поле | Колонка БД | Тип SQL | Ограничения |
|---|---|---|---|
| `id` | `id` | `BIGSERIAL` | `PRIMARY KEY` |
| `user` | `user_id` | `BIGINT` | `FK → users(id) NOT NULL` |
| `plant` | `plant_id` | `BIGINT` | `FK → plants(id) NOT NULL` |
| `confidence` | `confidence` | `DOUBLE PRECISION` | — |
| `identifiedAt` | `identified_at` | `TIMESTAMP` | — |

---

## Стратегии загрузки (Fetch)

| Связь | Стратегия | Обоснование |
|---|---|---|
| `Identification.user` | `LAZY` | Пользователь нужен не всегда при загрузке идентификации |
| `Identification.plant` | `LAZY` | Растение загружается по требованию |
| `User.identifications` | (через `mappedBy`) | Загружается только при явном обращении |

---

## DDL, генерируемый Hibernate

При `spring.jpa.hibernate.ddl-auto=update` Hibernate автоматически создаёт/обновляет схему. Эквивалентный DDL:

```sql
CREATE TABLE users (
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL
);

CREATE TABLE plants (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    image_url   VARCHAR(512)
);

CREATE TABLE identifications (
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT NOT NULL REFERENCES users(id),
    plant_id      BIGINT NOT NULL REFERENCES plants(id),
    confidence    DOUBLE PRECISION,
    identified_at TIMESTAMP
);
```

Полный DDL-скрипт: [ddl.sql](ddl.sql)
