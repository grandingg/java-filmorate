package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private final UserController controller = new UserController();

    @Test
    void shouldCreateUser() {
        User user = makeUser();

        User createdUser = controller.createUser(user);

        assertEquals(1, createdUser.getId());
        assertEquals("userLogin", createdUser.getLogin());
    }

    @Test
    void shouldUseLoginAsNameIfNameIsBlank() {
        User user = makeUser();
        user.setName("");

        User createdUser = controller.createUser(user);

        assertEquals(user.getLogin(), createdUser.getName());
    }

    @Test
    void shouldNotCreateUserWithEmptyEmail() {
        User user = makeUser();
        user.setEmail("");

        assertThrows(ValidationException.class, () -> controller.createUser(user));
    }

    @Test
    void shouldNotCreateUserWithEmailWithoutAt() {
        User user = makeUser();
        user.setEmail("user.mail.ru");

        assertThrows(ValidationException.class, () -> controller.createUser(user));
    }

    @Test
    void shouldNotCreateUserWithEmptyLogin() {
        User user = makeUser();
        user.setLogin("");

        assertThrows(ValidationException.class, () -> controller.createUser(user));
    }

    @Test
    void shouldNotCreateUserWithLoginWithSpaces() {
        User user = makeUser();
        user.setLogin("user login");

        assertThrows(ValidationException.class, () -> controller.createUser(user));
    }

    @Test
    void shouldCreateUserWithBirthdayToday() {
        User user = makeUser();
        user.setBirthday(LocalDate.now());

        User createdUser = controller.createUser(user);

        assertEquals(LocalDate.now(), createdUser.getBirthday());
    }

    @Test
    void shouldNotCreateUserWithBirthdayInFuture() {
        User user = makeUser();
        user.setBirthday(LocalDate.now().plusDays(1));

        assertThrows(ValidationException.class, () -> controller.createUser(user));
    }

    private User makeUser() {
        User user = new User();
        user.setEmail("user@mail.ru");
        user.setLogin("userLogin");
        user.setName("User Name");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        return user;
    }
}