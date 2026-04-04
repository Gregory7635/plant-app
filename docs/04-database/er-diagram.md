# ER-диаграмма

## Описание

Диаграмма сущность-связь описывает структуру базы данных PostgreSQL.

![ER-диаграмма](images/er-diagram.png)

## PlantUML-код

```plantuml
@startuml er-diagram
skinparam linetype ortho

title ER-диаграмма — База данных Plant App

entity "users" as users {
  * id : BIGSERIAL <<PK>>
  --
  * username : VARCHAR(255) <<UNIQUE, NOT NULL>>
  * password : VARCHAR(255) <<NOT NULL>>
  * role : VARCHAR(50) <<NOT NULL>>
}

entity "plants" as plants {
  * id : BIGSERIAL <<PK>>
  --
  * name : VARCHAR(255) <<NOT NULL>>
  description : TEXT
  wikipedia_url : VARCHAR(500)
  created_at : TIMESTAMP
}

entity "identifications" as identifications {
  * id : BIGSERIAL <<PK>>
  --
  image_path : VARCHAR(500)
  * probability : DOUBLE PRECISION <<NOT NULL>>
  * created_at : TIMESTAMP <<NOT NULL>>
  * user_id : BIGINT <<FK>>
  * plant_id : BIGINT <<FK>>
}

users ||--o{ identifications : "создаёт"
plants ||--o{ identifications : "определяется в"
@enduml
```

> Скопируй код на [plantuml.com](https://www.plantuml.com/plantuml/uml/) → скачай PNG → сохрани как `docs/04-database/images/er-diagram.png`
