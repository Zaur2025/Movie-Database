package com.example.moviedb; // 1️⃣ ТЕСТ В ТОМ ЖЕ ПАКЕТЕ

import com.example.moviedb.model.Movie;
import com.example.moviedb.repository.MovieRepository;
import org.junit.jupiter.api.Test; // 2️⃣ ФРЕЙМВОРК ДЛЯ ТЕСТОВ
import org.springframework.beans.factory.annotation.Autowired; // 3️⃣ SPRING ВСТАВЛЯЕТ ЗАВИСИМОСТИ
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest; // 4️⃣ МАГИЧЕСКАЯ АННОТАЦИЯ
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager; // 5️⃣ ПОМОЩНИК ДЛЯ РАБОТЫ С БД

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat; // 6️⃣ КРАСИВЫЕ ПРОВЕРКИ

@DataJpaTest // ← САМОЕ ВАЖНОЕ! ВКЛЮЧАЕТ «РЕЖИМ ТЕСТИРОВАНИЯ БД»
class MovieRepositoryTest {

    @Autowired
    private TestEntityManager entityManager; // 7️⃣ СПЕЦИАЛЬНЫЙ МЕНЕДЖЕР ДЛЯ ТЕСТОВ

    @Autowired
    private MovieRepository movieRepository; // 8️⃣ ТВОЙ НАСТОЯЩИЙ РЕПОЗИТОРИЙ

    // 1️⃣ Тест для findByTitle
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

    // 2️⃣ Тест для findByDirector
    @Test
    void findByDirector_shouldReturnMoviesByDirector() {
        // Arrange: сохраняем 2 фильма Нолана и 1 фильм другого режиссёра
        entityManager.persist(new Movie("Inception", "Nolan", 2010, Movie.MovieGenres.ФАНТАСТИКА, 8));
        entityManager.persist(new Movie("Interstellar", "Nolan", 2014, Movie.MovieGenres.ФАНТАСТИКА, 9));
        entityManager.persist(new Movie("Pulp Fiction", "Tarantino", 1994, Movie.MovieGenres.ТРИЛЛЕР, 10));

        // Act: ищем фильмы Нолана
        List<Movie> nolanMovies = movieRepository.findByDirector("Nolan");

        // Assert: проверяем, что нашли именно 2 фильма Нолана
        assertThat(nolanMovies)
                .hasSize(2)
                .extracting(Movie::getTitle) // извлекаем только названия
                .containsExactlyInAnyOrder("Inception", "Interstellar");
    }

    // 3️⃣ Тест для findByGenre
    @Test
    void findByGenre_shouldReturnMoviesByGenre() {
        // Arrange: сохраняем фильмы разных жанров
        entityManager.persist(new Movie("Inception", "Nolan", 2010, Movie.MovieGenres.ФАНТАСТИКА, 8));
        entityManager.persist(new Movie("Alien", "Scott", 1979, Movie.MovieGenres.УЖАСЫ, 9));
        entityManager.persist(new Movie("Interstellar", "Nolan", 2014, Movie.MovieGenres.ФАНТАСТИКА, 9));

        // Act: ищем фантастику
        List<Movie> sciFiMovies = movieRepository.findByGenre(Movie.MovieGenres.ФАНТАСТИКА);

        // Assert: проверяем, что нашли 2 фантастических фильма
        assertThat(sciFiMovies)
                .hasSize(2)
                .allMatch(movie -> movie.getGenre() == Movie.MovieGenres.ФАНТАСТИКА);
    }

    // 4️⃣ Тест для findByReleaseYear
    @Test
    void findByReleaseYear_shouldReturnMoviesByYear() {
        // Arrange: сохраняем фильмы разных годов
        entityManager.persist(new Movie("Inception", "Nolan", 2010, Movie.MovieGenres.ФАНТАСТИКА, 8));
        entityManager.persist(new Movie("The Social Network", "Fincher", 2010, Movie.MovieGenres.ДРАМА, 8));
        entityManager.persist(new Movie("Interstellar", "Nolan", 2014, Movie.MovieGenres.ФАНТАСТИКА, 9));

        // Act: ищем фильмы 2010 года
        List<Movie> movies2010 = movieRepository.findByReleaseYear(2010);

        // Assert
        assertThat(movies2010)
                .hasSize(2)
                .allMatch(movie -> movie.getReleaseYear() == 2010);
    }

    // 5️⃣ Тест для findMoviesWithMaxRatingNative
    @Test
    void findMoviesWithMaxRatingNative_shouldReturnHighestRatedMovies() {
        // Arrange: сохраняем фильмы с разными рейтингами
        entityManager.persist(new Movie("Good Movie", "Director1", 2020, Movie.MovieGenres.ДРАМА, 7));
        entityManager.persist(new Movie("Best Movie", "Director2", 2021, Movie.MovieGenres.ФАНТАСТИКА, 10));
        entityManager.persist(new Movie("Another Best", "Director3", 2022, Movie.MovieGenres.КОМЕДИЯ, 10));
        // Act: ищем фильмы с максимальным рейтингом
        entityManager.persist(new Movie("Average", "Director4", 2023, Movie.MovieGenres.ТРИЛЛЕР, 5));

        List<Movie> bestMovies = movieRepository.findMoviesWithMaxRatingNative();

        // Assert: проверяем, что получили только фильмы с рейтингом 10
        assertThat(bestMovies)
                .hasSize(2)
                .allMatch(movie -> movie.getRating() == 10)
                .extracting(Movie::getTitle)
                .containsExactlyInAnyOrder("Best Movie", "Another Best");
    }

    // 6️⃣ Тест на крайний случай: когда ничего не найдено
    @Test
    void findByTitle_whenNotExist_shouldReturnNull() {
        // Act: ищем несуществующий фильм
        Movie found = movieRepository.findByTitle("Non-existent Movie");

        // Assert: проверяем, что получили null
        assertThat(found).isNull();
    }

    @Test
    void findByDirector_whenNotExist_shouldReturnEmptyList() {
        List<Movie> found = movieRepository.findByDirector("Non-exist Director");
        assertThat(found).isEmpty(); // Проверяем ПУСТОЙ список, а не null!
    }

    @Test
    void findByGenre_whenNotExist_shouldReturnEmptyList() {
        List<Movie> found = movieRepository.findByGenre(Movie.MovieGenres.ДОКУМЕНТАЛЬНЫЙ);
        assertThat(found).isEmpty();
    }

    @Test
    void findByReleaseYear_whenNotExist_shouldReturnEmptyList() {
        List<Movie> found = movieRepository.findByReleaseYear(2222);
        assertThat(found).isEmpty();
    }
}