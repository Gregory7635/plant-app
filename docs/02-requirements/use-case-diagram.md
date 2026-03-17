# Use Case диаграмма

## Описание

Диаграмма вариантов использования описывает функциональные требования с точки зрения пользователей системы.

![Use Case диаграмма](images/use-case-diagram.png)

## PlantUML-код

```plantuml
@startuml use-case-diagram
left to right direction
skinparam actorStyle awesome

title Use Case диаграмма — Plant App

actor "Гость" as Guest
actor "Пользователь" as User
actor "Администратор" as Admin
actor "Plant.id API" as ExtAPI <<external>>
actor "Wikipedia API" as WikiAPI <<external>>

User --|> Guest
Admin --|> User

rectangle "Plant App" {
  usecase "UC1: Просмотр\nглавной страницы" as UC1
  usecase "UC2: Регистрация" as UC2
  usecase "UC3: Авторизация" as UC3
  usecase "UC4: Загрузка фото\nдля идентификации" as UC4
  usecase "UC5: Получение результата\nидентификации" as UC5
  usecase "UC6: Вызов Plant.id API" as UC6
  usecase "UC7: Получение описания\nиз Wikipedia" as UC7
  usecase "UC8: Просмотр истории\nзапросов" as UC8
  usecase "UC9: Просмотр карточки\nрастения" as UC9
  usecase "UC10: Управление\nпользователями" as UC10
}

Guest --> UC1
Guest --> UC2
Guest --> UC3

User --> UC4
User --> UC8
User --> UC9

UC4 ..> UC5 : <<include>>
UC5 ..> UC6 : <<include>>
UC5 ..> UC7 : <<include>>

ExtAPI --> UC6
WikiAPI --> UC7

Admin --> UC10
@enduml
```

> Скопируй код на [plantuml.com](https://www.plantuml.com/plantuml/uml/) → скачай PNG → сохрани как `docs/02-requirements/images/use-case-diagram.png`
