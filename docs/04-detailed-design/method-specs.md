# Спецификация методов

## Проект: Веб-приложение для идентификации растений

---

## PlantService

### `identifyPlantAndSave(MultipartFile file, User user)`

| Атрибут | Значение |
|---|---|
| **Класс** | `com.example.plantapp.mediator.PlantService` |
| **Видимость** | `public` |
| **Возвращаемый тип** | `Identification` |
| **Слой PCMEF** | Mediator |

**Параметры:**

| Параметр | Тип | Описание |
|---|---|---|
| `file` | `MultipartFile` | Загруженное изображение растения |
| `user` | `User` | Аутентифицированный пользователь |

**Алгоритм:**
1. Конвертировать `MultipartFile` в Base64
2. Отправить POST-запрос к Plant.id API с изображением
3. Распарсить JSON-ответ, извлечь название растения и вероятность
4. Найти растение по имени в БД или создать новую запись (`findOrCreatePlant`)
5. Получить описание растения из Wikipedia API
6. Создать объект `Identification` и сохранить через `IdentificationRepository`
7. Вернуть созданный `Identification`

**Исключения:**
- `IOException` — ошибка чтения файла
- `RestClientException` — недоступность Plant.id API

---

### `findOrCreatePlant(String name)`

| Атрибут | Значение |
|---|---|
| **Видимость** | `private` |
| **Возвращаемый тип** | `Plant` |

**Алгоритм:**
1. Поиск `Plant` по имени через `plantRepository.findByName(name)`
2. Если найден — вернуть существующий (Identity Map pattern)
3. Если не найден — создать новый `Plant`, запросить описание из Wikipedia, сохранить

---

### `getAllPlants()`

| Атрибут | Значение |
|---|---|
| **Видимость** | `public` |
| **Возвращаемый тип** | `List<Plant>` |

Делегирует вызов `plantRepository.findAll()`. Используется REST API.

---

## UserService

### `registerUser(String username, String password)`

| Атрибут | Значение |
|---|---|
| **Класс** | `com.example.plantapp.mediator.UserService` |
| **Видимость** | `public` |
| **Возвращаемый тип** | `void` |

**Параметры:**

| Параметр | Тип | Описание |
|---|---|---|
| `username` | `String` | Логин нового пользователя |
| `password` | `String` | Пароль в открытом виде |

**Алгоритм:**
1. Проверить, не занят ли `username` (если занят — выбросить `IllegalArgumentException`)
2. Захешировать пароль через `BCryptPasswordEncoder`
3. Создать `User` с ролью `ROLE_USER`
4. Сохранить через `userRepository.save()`

---

### `getCurrentUser(String username)`

| Атрибут | Значение |
|---|---|
| **Видимость** | `public` |
| **Возвращаемый тип** | `User` |

Находит пользователя по username, выбрасывает `UsernameNotFoundException` если не найден.

---

## WikipediaService

### `getDescription(String plantName)`

| Атрибут | Значение |
|---|---|
| **Класс** | `com.example.plantapp.mediator.WikipediaService` |
| **Видимость** | `public` |
| **Возвращаемый тип** | `String` |

**Алгоритм:**
1. Сформировать запрос к Wikipedia API: `https://ru.wikipedia.org/api/rest_v1/page/summary/{plantName}`
2. Отправить GET-запрос через `RestTemplate`
3. Распарсить JSON, вернуть поле `extract`
4. При ошибке (404, сетевой сбой) — вернуть пустую строку `""`

---

## PlantController (Control)

### `identify(MultipartFile file, Principal principal, Model model)`

| Атрибут | Значение |
|---|---|
| **Класс** | `com.example.plantapp.control.PlantController` |
| **HTTP метод** | `POST /identify` |
| **Возвращаемый тип** | `ResponseEntity<Map<String, Object>>` |

**Алгоритм:**
1. Получить текущего пользователя через `userService.getCurrentUser(principal.getName())`
2. Вызвать `plantService.identifyPlantAndSave(file, user)`
3. Вернуть JSON с результатом идентификации (для Fetch API)

---

### `history(Principal principal, Model model)`

| Атрибут | Значение |
|---|---|
| **HTTP метод** | `GET /history` |
| **Возвращаемый тип** | `String` (имя Thymeleaf-шаблона) |

Получает список идентификаций текущего пользователя, передаёт в модель, возвращает `"history"`.

---

## Сводная таблица методов

| Класс | Метод | HTTP | Возвращает |
|---|---|---|---|
| `PlantService` | `identifyPlantAndSave` | — | `Identification` |
| `PlantService` | `getAllPlants` | — | `List<Plant>` |
| `PlantService` | `getPlantById` | — | `Optional<Plant>` |
| `UserService` | `registerUser` | — | `void` |
| `UserService` | `getCurrentUser` | — | `User` |
| `WikipediaService` | `getDescription` | — | `String` |
| `PlantController` | `identify` | POST /identify | `ResponseEntity` |
| `PlantController` | `history` | GET /history | `String` (view) |
| `PlantController` | `plantCard` | GET /plants/{id} | `String` (view) |
| `PlantRestController` | `getAllPlants` | GET /api/plants | `List<Plant>` |
| `PlantRestController` | `getPlantById` | GET /api/plants/{id} | `Plant` |
| `IdentificationRestController` | `getAllIdentifications` | GET /api/identifications | `List<Identification>` |
| `UserRestController` | `getCurrentUser` | GET /api/users/me | `User` |
