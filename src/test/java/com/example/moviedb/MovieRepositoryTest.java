package com.example.moviedb; // 1️⃣ ТЕСТ В ТОМ ЖЕ ПАКЕТЕ
import org.junit.jupiter.api.Test; // 2️⃣ ФРЕЙМВОРК ДЛЯ ТЕСТОВ
import org.springframework.beans.factory.annotation.Autowired; // 3️⃣ SPRING ВСТАВЛЯЕТ ЗАВИСИМОСТИ
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest; // 4️⃣ МАГИЧЕСКАЯ АННОТАЦИЯ
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager; // 5️⃣ ПОМОЩНИК ДЛЯ РАБОТЫ С БД
import static org.assertj.core.api.Assertions.assertThat; // 6️⃣ КРАСИВЫЕ ПРОВЕРКИ

@DataJpaTest // ← САМОЕ ВАЖНОЕ! ВКЛЮЧАЕТ «РЕЖИМ ТЕСТИРОВАНИЯ БД»
class MovieRepositoryTest {

    @Autowired
    private TestEntityManager entityManager; // 7️⃣ СПЕЦИАЛЬНЫЙ МЕНЕДЖЕР ДЛЯ ТЕСТОВ

    @Autowired
    private MovieRepository movieRepository; // 8️⃣ ТВОЙ НАСТОЯЩИЙ РЕПОЗИТОРИЙ

    @Test
    void whenFindByTitle_thenReturnMovie() {
        // 9️⃣ ПОДГОТОВКА: СОХРАНЯЕМ ФИЛЬМ В БД (НО НЕ ЧЕРЕЗ РЕПОЗИТОРИЙ!)
        Movie savedMovie = entityManager.persist(
                new Movie("Inception", "Nolan", 2010, Movie.MovieGenres.ФАНТАСТИКА, 8)
        );

        // 🔟 ДЕЙСТВИЕ: ИЩЕМ ЧЕРЕЗ РЕПОЗИТОРИЙ (Spring Data JPA)
        Movie found = movieRepository.findByTitle("Inception");

        // 1️⃣1️⃣ ПРОВЕРКА: УБЕЖДАЕМСЯ, ЧТО НАШЛИ ТОТ ЖЕ ФИЛЬМ
        assertThat(found).isNotNull(); // Проверяем, что что-то нашли
        assertThat(found.getDirector()).isEqualTo("Nolan"); // Проверяем поле
        assertThat(found.getId()).isEqualTo(savedMovie.getId()); // Сравниваем ID
    }
}