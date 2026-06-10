package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private final FilmController controller = new FilmController();

    @Test
    void shouldCreateFilm() {
        Film film = makeFilm();

        Film createdFilm = controller.createFilm(film);

        assertEquals(1, createdFilm.getId());
        assertEquals("Film name", createdFilm.getName());
    }

    @Test
    void shouldNotCreateFilmWithEmptyName() {
        Film film = makeFilm();
        film.setName("");

        assertThrows(ValidationException.class, () -> controller.createFilm(film));
    }

    @Test
    void shouldNotCreateFilmWithDescriptionMoreThan200Symbols() {
        Film film = makeFilm();
        film.setDescription("a".repeat(201));

        assertThrows(ValidationException.class, () -> controller.createFilm(film));
    }

    @Test
    void shouldCreateFilmWithReleaseDateOnBoundary() {
        Film film = makeFilm();
        film.setReleaseDate(LocalDate.of(1895, 12, 28));

        Film createdFilm = controller.createFilm(film);

        assertEquals(LocalDate.of(1895, 12, 28), createdFilm.getReleaseDate());
    }

    @Test
    void shouldNotCreateFilmWithReleaseDateBeforeBoundary() {
        Film film = makeFilm();
        film.setReleaseDate(LocalDate.of(1895, 12, 27));

        assertThrows(ValidationException.class, () -> controller.createFilm(film));
    }

    @Test
    void shouldNotCreateFilmWithNegativeDuration() {
        Film film = makeFilm();
        film.setDuration(-1);

        assertThrows(ValidationException.class, () -> controller.createFilm(film));
    }

    @Test
    void shouldNotCreateFilmWithZeroDuration() {
        Film film = makeFilm();
        film.setDuration(0);

        assertThrows(ValidationException.class, () -> controller.createFilm(film));
    }

    private Film makeFilm() {
        Film film = new Film();
        film.setName("Film name");
        film.setDescription("Film description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);
        return film;
    }
}