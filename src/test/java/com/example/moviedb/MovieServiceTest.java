package com.example.moviedb;

import com.example.moviedb.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // 1. Включаем поддержку Mockito
class MovieServiceTest {

    @Mock // 2. Создаём МОК репозитория (заглушку)
    private MovieRepository movieRepository;

    @InjectMocks // 3. Внедряем моки в тестируемый сервис
    private MovieService movieService;

    // 📌 Тест 1: Успешное создание фильма
    @Test
    void createMovie_withValidData_shouldSaveAndReturnMovie() {
        // Arrange
        Movie newMovie = new Movie("Inception", "Nolan", 2010, Movie.MovieGenres.ФАНТАСТИКА, 8);
        Movie savedMovie = new Movie("Inception", "Nolan", 2010, Movie.MovieGenres.ФАНТАСТИКА, 8);
        savedMovie.setId(1L); // У сохранённого фильма есть ID

        // Настраиваем поведение мока
        when(movieRepository.findByTitle("Inception")).thenReturn(null); // Такого фильма ещё нет
        when(movieRepository.save(any(Movie.class))).thenReturn(savedMovie); // При сохранении вернём объект с ID

        // Act
        Movie result = movieService.createMovie(newMovie);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Inception");

        // Verify: проверяем, что методы вызывались
        verify(movieRepository, times(1)).findByTitle("Inception");
        verify(movieRepository, times(1)).save(newMovie);
        verifyNoMoreInteractions(movieRepository); // И больше НИЧЕГО не вызывалось
    }

    // 📌 Тест 2: Попытка создать фильм с дублирующимся названием
    @Test
    void createMovie_withDuplicateTitle_shouldThrowException() {
        // Arrange
        Movie existingMovie = new Movie("Inception", "Nolan", 2010, Movie.MovieGenres.ФАНТАСТИКА, 8);
        existingMovie.setId(1L);

        Movie newMovie = new Movie("Inception", "Another Director", 2023, Movie.MovieGenres.КОМЕДИЯ, 5);

        // Настраиваем: findByTitle возвращает существующий фильм
        when(movieRepository.findByTitle("Inception")).thenReturn(existingMovie);

        // Act & Assert
        assertThatThrownBy(() -> movieService.createMovie(newMovie))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Фильм с таким именем уже есть в базе!");

        // Verify: save() НЕ должен вызываться!
        verify(movieRepository, never()).save(any());
        verifyNoMoreInteractions(movieRepository); // И больше НИЧЕГО не вызывалось
    }

    // 📌 Тест 3: Создание фильма с пустым названием
    @Test
    void createMovie_withEmptyTitle_shouldThrowValidationException() {
        // Arrange
        Movie invalidMovie = new Movie("   ", "Director", 2020, Movie.MovieGenres.ДРАМА, 5);

        // Act & Assert
        assertThatThrownBy(() -> movieService.createMovie(invalidMovie))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Movie title cannot be empty");

        // Verify: репозиторий вообще не должен вызываться
        verify(movieRepository, never()).findByTitle(anyString());
        verify(movieRepository, never()).save(any());
        verifyNoMoreInteractions(movieRepository); // И больше НИЧЕГО не вызывалось
    }

    // 📌 Тест 4: Успешное получение фильма по ID
    @Test
    void getMovieById_whenExists_shouldReturnMovie() {
        // Arrange
        Long movieId = 1L;
        Movie expectedMovie = new Movie("Inception", "Nolan", 2010, Movie.MovieGenres.ФАНТАСТИКА, 8);
        expectedMovie.setId(movieId);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(expectedMovie));

        // Act
        Movie result = movieService.getMovieById(movieId);

        // Assert
        assertThat(result).isEqualTo(expectedMovie);
        verify(movieRepository, times(1)).findById(movieId);
        verifyNoMoreInteractions(movieRepository); // И больше НИЧЕГО не вызывалось
    }

    // 📌 Тест 5: Получение несуществующего фильма по ID
    @Test
    void getMovieById_whenNotExists_shouldThrowException() {
        // Arrange
        Long nonExistentId = 999L;
        when(movieRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> movieService.getMovieById(nonExistentId))
                .isInstanceOf(com.example.moviedb.exception.MovieNotFoundException.class)
                .hasMessage("Movie with id " + nonExistentId + " not found");

        verify(movieRepository, times(1)).findById(nonExistentId);
        verifyNoMoreInteractions(movieRepository); // И больше НИЧЕГО не вызывалось
    }

    // 📌 Тест 6: Получение всех фильмов
    @Test
    void getAllMovies_shouldReturnList() {
        // Arrange
        List<Movie> movies = List.of(
                new Movie("Inception", "Nolan", 2010, Movie.MovieGenres.ФАНТАСТИКА, 8),
                new Movie("Interstellar", "Nolan", 2014, Movie.MovieGenres.ФАНТАСТИКА, 9)
        );

        when(movieRepository.findAll()).thenReturn(movies);

        // Act
        List<Movie> result = movieService.getAllMovies();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsAll(movies);
        verify(movieRepository, times(1)).findAll();
        verifyNoMoreInteractions(movieRepository); // И больше НИЧЕГО не вызывалось
    }

    // 📌 Тест 7: Успешное удаление фильма
    @Test
    void deleteMovie_shouldCallRepository() {
        // Arrange
        Long movieId = 1L;
        doNothing().when(movieRepository).deleteById(movieId);

        // Act
        String result = movieService.deleteMovie(movieId);

        // Assert
        assertThat(result).isEqualTo("Movie deleted successfully");
        verify(movieRepository, times(1)).deleteById(movieId);
        verifyNoMoreInteractions(movieRepository); // И больше НИЧЕГО не вызывалось
    }

    // 📌 Тест 8: Поиск по режиссёру (делегирование репозиторию)
    @Test
    void getMoviesByDirector_shouldDelegateToRepository() {
        // Arrange
        String director = "Nolan";
        List<Movie> nolanMovies = List.of(
                new Movie("Inception", "Nolan", 2010, Movie.MovieGenres.ФАНТАСТИКА, 8),
                new Movie("Interstellar", "Nolan", 2014, Movie.MovieGenres.ФАНТАСТИКА, 9)
        );

        when(movieRepository.findByDirector(director)).thenReturn(nolanMovies);

        // Act
        List<Movie> result = movieService.getMoviesByDirector(director);

        // Assert
        assertThat(result).isEqualTo(nolanMovies);
        verify(movieRepository, times(1)).findByDirector(director);
        verifyNoMoreInteractions(movieRepository); // И больше НИЧЕГО не вызывалось
    }

    // Тест 9: Поиск по жанру (делегирование репозиторию), happy path (фильмы найдены)
    @Test
    void getMoviesByGenre_shouldDelegateToRepository() {
        //Arrange
        Movie.MovieGenres genre = Movie.MovieGenres.ДЛЯ_ВЗРОСЛЫХ;
        List<Movie> adultFilms = List.of(
                new Movie("Красная шапочка и семь гномов. Часть 1", "Некэмерон", 2022, Movie.MovieGenres.ДЛЯ_ВЗРОСЛЫХ, 7),
                new Movie("Красная шапочка и семь гномов. Часть 2", "Некэмерон", 2023, Movie.MovieGenres.ДЛЯ_ВЗРОСЛЫХ, 8)
        );

        when(movieRepository.findByGenre(genre)).thenReturn(adultFilms);

        //Act
        List<Movie> result = movieService.getMoviesByGenre(genre);

        //Assert
        assertThat(result).isEqualTo(adultFilms);
        // Дополнительные проверки (можно добавить после assertThat). Но это не обязательно — твой тест уже полноценный.
        assertThat(result)
                .hasSize(2)
                .extracting(Movie::getTitle)
                .containsExactly(
                        "Красная шапочка и семь гномов. Часть 1",
                        "Красная шапочка и семь гномов. Часть 2"
                );
        // Verify: проверяем, что методы вызывались
        verify(movieRepository, times(1)).findByGenre(genre);
        verifyNoMoreInteractions(movieRepository);
    }

    //Тест 10: поиск по жанру, edge case (фильмы не найдены)
    @Test
    void getMoviesByGenre_whenNoMovies_shouldReturnEmptyList() {
        // Arrange
        Movie.MovieGenres genre = Movie.MovieGenres.ДОКУМЕНТАЛЬНЫЙ;

        // Настрой мока: возвращаем пустой список
        when(movieRepository.findByGenre(genre)).thenReturn(Collections.emptyList());

        // Act
        List<Movie> result = movieService.getMoviesByGenre(genre);

        // Assert
        assertThat(result).isEmpty();
        verify(movieRepository, times(1)).findByGenre(genre);
        verifyNoMoreInteractions(movieRepository);
    }

    //Тест 11: поиск по году выхода, happy path (фильмы найдены)
    @Test
    void getMoviesByReleaseYear_shouldDelegateToRepository() {
        //Arrange
        int releaseYear = 2022;

        List<Movie> filmsWithReleaseYear2022 = List.of(
                new Movie("Терминатор 1", "Кэмерон", 1991, Movie.MovieGenres.ФАНТАСТИКА, 8),
                new Movie("Трудный ребенок 2", "Некэмерон", 1991, Movie.MovieGenres.КОМЕДИЯ, 7),
                new Movie("Терминатор 3", "Кэмерон", 1999, Movie.MovieGenres.ФАНТАСТИКА, 6)
        );
        when(movieRepository.findByReleaseYear(releaseYear)).thenReturn(filmsWithReleaseYear2022);

        //Act
        List<Movie> result = movieService.getMoviesByReleaseYear(releaseYear);

        //Assert
        assertThat(result).isEqualTo(filmsWithReleaseYear2022);

        //Verify
        verify(movieRepository, times(1)).findByReleaseYear(releaseYear);
        verifyNoMoreInteractions(movieRepository);
    }

    //Тест 12: поиск по году выхода, edge case (фильмы не найдены)
    /*void getMoviesByReleaseYear_shouldReturnEmptyList() {

    }*/
}