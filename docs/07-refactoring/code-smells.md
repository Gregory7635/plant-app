# Анализ «запахов кода» (Code Smells)

## Проект: Веб-приложение для идентификации растений

---

## Что такое «запахи кода»

«Запахи кода» (Code Smells) — признаки в исходном коде, которые указывают на возможные проблемы с его структурой или дизайном. Они не являются ошибками сами по себе, но усложняют понимание, сопровождение и тестирование.

---

## Выявленные запахи и применённые решения

### 1. Long Method (Длинный метод)

**Проблема:** Метод `identifyPlantAndSave()` в `PlantService` выполнял несколько задач последовательно: вызов Plant.id API, парсинг JSON, поиск/создание растения в БД, получение описания из Wikipedia, сохранение идентификации.

**До рефакторинга:**
```java
// Один метод > 80 строк
public Identification identifyPlantAndSave(MultipartFile file, User user) {
    // 1. Вызов Plant.id API
    // 2. Парсинг ответа
    // 3. Поиск или создание Plant
    // 4. Вызов Wikipedia API
    // 5. Сохранение Identification
    // ... 80+ строк ...
}
```

**Решение:** Декомпозиция на отдельные private-методы и выделение `WikipediaService`.

```java
public Identification identifyPlantAndSave(MultipartFile file, User user) {
    String plantName = callPlantIdApi(file);
    Plant plant = findOrCreatePlant(plantName);
    return saveIdentification(plant, user);
}
```

---

### 2. Feature Envy (Зависть к чужим данным)

**Проблема:** В контроллере `PlantController` присутствовала логика поиска пользователя и формирования ответа, которая относится к слою Mediator.

**Решение:** Логика перенесена в `PlantService` и `UserService`. Контроллер только принимает запрос и передаёт результат в представление.

```java
// До: в контроллере
User user = userRepository.findByUsername(principal.getName()).orElseThrow();

// После: в сервисе
User user = userService.getCurrentUser(principal.getName());
```

---

### 3. Duplicate Code (Дублирование кода)

**Проблема:** Получение текущего аутентифицированного пользователя повторялось в нескольких контроллерах через одинаковый блок кода.

**Решение:** Вынесено в метод `UserService.getCurrentUser(String username)`, который используется из всех контроллеров через единую точку.

---

### 4. Magic Numbers/Strings (Магические числа)

**Проблема:** В коде встречались hardcoded URL Plant.id API и константы без именования.

**До:**
```java
String url = "https://api.plant.id/v3/identification";
```

**Решение:** Вынос конфигурационных значений в `application.properties`:
```properties
plantid.api.url=https://api.plant.id/v3/identification
plantid.api.key=${PLANT_ID_API_KEY}
```

И чтение через `@Value`:
```java
@Value("${plantid.api.url}")
private String plantIdApiUrl;
```

---

### 5. Primitive Obsession (Злоупотребление примитивами)

**Проблема:** Данные пользователя (имя, пароль) передавались как отдельные строки между слоями.

**Решение:** Использование Spring Security `UserDetails` как структуры данных для передачи информации об аутентифицированном пользователе.

---

## Применённые паттерны рефакторинга

| Запах | Паттерн решения | Применено |
|---|---|---|
| Long Method | Extract Method | `PlantService`: декомпозиция `identifyPlantAndSave` |
| Feature Envy | Move Method | Логика из Controller перенесена в Service |
| Duplicate Code | Extract Method / Template Method | `UserService.getCurrentUser()` |
| Magic Strings | Replace Magic Literal | `@Value` из `application.properties` |
| Primitive Obsession | Introduce Parameter Object | Spring Security `UserDetails` |

---

## Результат

После рефакторинга:
- Средняя длина метода снизилась с ~50 до ~15 строк
- Контроллеры не содержат бизнес-логику
- Конфигурационные параметры вынесены из кода
- Дублирование устранено
