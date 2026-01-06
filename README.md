🎬 Movie Database API

Spring Boot REST API для управления фильмами с аутентификацией, авторизацией и полным тестированием + REST API Hello Controller.

🚀 Быстрый старт

git clone https://github.com/Zaur2025/Movie-Database.git
cd Movie-Database
mvn spring-boot:run

Приложение запускается на: http://localhost:8080

🚀 Функционал
- CRUD операции для фильмов
- Поиск по названию, режиссёру, жанру
- Фильтрация по году выпуска и рейтингу
- Получение топ фильмов по рейтингу

🛠 Технологии
- Java 17 + Spring Boot 3
- Spring Security (аутентификация/авторизация)
- Spring Data JPA + H2 Database
- JUnit 5 + **Mockito (тестирование)
- Maven (сборка)

📡 Основные Endpoints

🎬 Фильмы (требуют аутентификации)
| Метод | Endpoint | Описание | Доступ |
|-------|----------|----------|--------|
| `GET` | `/api/movies` | Все фильмы | USER, ADMIN |
| `GET` | `/api/movies/{id}` | Фильм по ID | USER, ADMIN |
| `POST` | `/api/movies` | Добавить фильм | ADMIN |
| `DELETE` | `/api/movies/{id}` | Удалить фильм | ADMIN |

🔍 Поиск и фильтрация
- `GET /api/movies/by-title?title=...` - По названию
- `GET /api/movies/by-director?director=...` - По режиссеру
- `GET /api/movies/by-genre?genre=...` - По жанру
- `GET /api/movies/by-releaseyear?releaseyear=...` - По году
- `GET /api/movies/best` - Топ фильмы по рейтингу

🔐 Аутентификация
- `POST /api/auth/register` - Регистрация
- `GET /login` - Форма входа

Тестовые пользователи:
- USER: `user` / `password`
- ADMIN: `admin` / `admin`

🏗 Архитектура
Controller → Service → Repository → Database
- Controller: REST endpoints (`@RestController`)
- Service:** Бизнес-логика (`@Service`)
- Repository: Работа с БД (`JpaRepository`)
- Entity: JPA сущности (`@Entity`)

🧪 Тестирование
# Запуск всех тестов
mvn test

# Только service тесты
mvn test -Dtest="*ServiceTest"

📊 База данных
- H2 Console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:moviedb`
- User: `sa`, Password: (пусто)

🚀 Примеры запросов
Создание фильма (ADMIN):
curl -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -u admin:admin \
  -d '{"title": "Inception", "director": "Nolan", "releaseYear": 2010, "genre": "Sci-Fi", "rating": 8.8}'

Поиск по жанру (USER):
curl -X GET "http://localhost:8080/api/movies/by-genre?genre=Sci-Fi" -u user:password

📁 Структура проекта
src/main/java/com/example/moviedatabase/
├── controller/     # REST контроллеры
├── service/        # Бизнес-логика
├── repository/     # Spring Data репозитории
├── entity/         # JPA сущности
├── config/         # Конфигурации
├── exception/      # Обработка ошибок
└── dto/            # Data Transfer Objects

📝 Планы развития
- [ ] JWT аутентификация
- [ ] Пагинация результатов
- [ ] Swagger документация
- [ ] Docker контейнеризация
- [ ] Миграция на PostgreSQL

📄 Лицензия
MIT License

---
Автор:** Zaur  
GitHub:** https://github.com/Zaur2025/Movie-Database  
Обновлено: январь 2026*