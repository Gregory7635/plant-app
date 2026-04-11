# Sequence-диаграммы

## 1. Идентификация растения

![Sequence: Идентификация](images/seq-identify.png)

### PlantUML-код

```plantuml
@startuml seq-identify
title Sequence: Идентификация растения

actor Пользователь as User
participant "index.html\n(Presentation)" as View
participant "PlantController\n(Control)" as Ctrl
participant "PlantService\n(Mediator)" as Svc
participant "Plant.id API\n(External)" as PlantAPI
participant "WikipediaService\n(Mediator)" as Wiki
participant "PlantRepository\n(Foundation)" as PlantRepo
participant "IdentificationRepository\n(Foundation)" as IdentRepo
database "PostgreSQL" as DB

User -> View: выбирает файл, нажимает "Определить"
View -> Ctrl: POST /identify (multipartFile)
Ctrl -> Svc: identifyPlantAndSave(file)

Svc -> Svc: encodeToBase64(file)
Svc -> PlantAPI: POST /identification {images: [base64]}
PlantAPI --> Svc: {name, probability}

Svc -> Wiki: getDescription(name)
Wiki --> Svc: description, wikipediaUrl

Svc -> PlantRepo: findByName(name)
PlantRepo -> DB: SELECT * FROM plants WHERE name=?
DB --> PlantRepo: Plant | null

alt растение не найдено
  Svc -> PlantRepo: save(newPlant)
  PlantRepo -> DB: INSERT INTO plants
  DB --> PlantRepo: savedPlant
end

Svc -> IdentRepo: save(identification)
IdentRepo -> DB: INSERT INTO identifications
DB --> IdentRepo: savedIdentification

IdentRepo --> Svc: identification
Svc --> Ctrl: identification
Ctrl --> View: redirect → result.html
View --> User: страница с результатом
@enduml
```

> Сохрани PNG как `docs/05-design/images/seq-identify.png`

---

## 2. Регистрация пользователя

![Sequence: Регистрация](images/seq-register.png)

### PlantUML-код

```plantuml
@startuml seq-register
title Sequence: Регистрация пользователя

actor Пользователь as User
participant "register.html\n(Presentation)" as View
participant "AuthController\n(Control)" as Ctrl
participant "UserService\n(Mediator)" as Svc
participant "UserRepository\n(Foundation)" as Repo
database "PostgreSQL" as DB

User -> View: заполняет форму (username, password)
View -> Ctrl: POST /register
Ctrl -> Svc: registerUser(username, password)

Svc -> Repo: findByUsername(username)
Repo -> DB: SELECT * FROM users WHERE username=?
DB --> Repo: null (не найден)

Svc -> Svc: BCrypt.hash(password)
Svc -> Repo: save(newUser)
Repo -> DB: INSERT INTO users
DB --> Repo: savedUser

Svc --> Ctrl: success
Ctrl --> View: redirect /login
View --> User: страница входа
@enduml
```

> Сохрани PNG как `docs/05-design/images/seq-register.png`

---

## 3. Просмотр истории запросов

![Sequence: История](images/seq-history.png)

### PlantUML-код

```plantuml
@startuml seq-history
title Sequence: Просмотр истории запросов

actor Пользователь as User
participant "history.html\n(Presentation)" as View
participant "PlantController\n(Control)" as Ctrl
participant "PlantService\n(Mediator)" as Svc
participant "IdentificationRepository\n(Foundation)" as Repo
database "PostgreSQL" as DB

User -> View: GET /history
View -> Ctrl: GET /history
Ctrl -> Svc: getHistoryForCurrentUser()

Svc -> Svc: getCurrentUsername()\n(из SecurityContext)
Svc -> Repo: findByUserOrderByCreatedAtDesc(user)
Repo -> DB: SELECT * FROM identifications\nWHERE user_id=? ORDER BY created_at DESC
DB --> Repo: List<Identification>

Repo --> Svc: history
Svc --> Ctrl: history
Ctrl --> View: model.addAttribute("history", ...)
View --> User: таблица с историей
@enduml
```

> Сохрани PNG как `docs/05-design/images/seq-history.png`
