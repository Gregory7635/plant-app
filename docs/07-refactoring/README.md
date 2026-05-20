# 07 — Рефакторинг

## Содержание

- Анализ «запахов кода»
- Паттерн Data Mapper
- Паттерн Identity Map

## Применённые паттерны

### Data Mapper
JPA + Hibernate отделяет объекты предметной области (`Plant`, `User`, `Identification`) от схемы БД. Изменение схемы не требует изменения бизнес-логики.

### Identity Map
Реализован на двух уровнях:
1. Hibernate Session Cache — уникальность объектов в рамках транзакции
2. `PlantService.identifyPlantAndSave()` — проверка существования растения через `findByName()` перед созданием новой записи

### Lazy Load
Связи `@ManyToOne` в `Identification` используют `FetchType.LAZY` — связанные объекты загружаются только при обращении.
