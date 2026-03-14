# Контекстная диаграмма IDEF0

## Описание

Диаграмма A-0 описывает систему как единый процесс с входами, выходами, управляющими воздействиями и механизмами.

![Контекстная диаграмма IDEF0](images/idef0-context.png)

## PlantUML-код

```plantuml
@startuml idef0-context
skinparam rectangle {
  BackgroundColor #FEFEFE
  BorderColor #555
}
skinparam arrow {
  Color #333
}

title IDEF0 — Контекстная диаграмма A-0\nСистема идентификации растений

rectangle "A-0\nИдентифицировать растение\nпо фотографии" as A0 #LightYellow

' Inputs (слева)
[Фотография растения] --> A0
[Данные пользователя\n(логин/пароль)] --> A0

' Outputs (справа)
A0 --> [Результат идентификации\n(название, описание, вероятность)]
A0 --> [История запросов]

' Controls (сверху)
[Требования безопасности\n(BCrypt, Spring Security)] -down-> A0
[Plant.id API\n(правила классификации)] -down-> A0

' Mechanisms (снизу)
A0 -down-> [Java Spring Boot\nПриложение]
A0 -down-> [PostgreSQL]
A0 -down-> [Plant.id API]
A0 -down-> [Wikipedia API]
@enduml
```

> Скопируй код выше на [plantuml.com](https://www.plantuml.com/plantuml/uml/), скачай PNG и сохрани как `docs/01-business-model/images/idef0-context.png`
