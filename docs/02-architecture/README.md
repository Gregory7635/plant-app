# Этап 3 — Архитектура

## Содержание

- [PCMEF-диаграмма](pcmef-diagram.md)
- [ADR — Архитектурные решения](adr.md)
- [Описание интерфейсов](interfaces.md)

## Описание архитектуры

Проект построен по архитектурному паттерну **PCMEF** (Presentation–Control–Mediator–Entity–Foundation).

### Принципы

- Зависимости направлены строго сверху вниз: `P → C → M → E → F`
- Слои общаются через интерфейсы (Spring бины)
- Слой Mediator не знает о Presentation
- Foundation не содержит бизнес-логики

### Маппинг на код

| Слой | Пакет | Классы |
|---|---|---|
| Control | `control/` | `PlantController`, `AuthController`, `PlantRestController`, `IdentificationRestController`, `UserRestController`, `ApiIdentificationController` |
| Mediator | `mediator/` | `PlantService`, `UserService`, `WikipediaService`, `CustomUserDetailsService` |
| Entity | `entity/` | `Plant`, `User`, `Identification` |
| Foundation | `foundation/` | `PlantRepository`, `UserRepository`, `IdentificationRepository` |
