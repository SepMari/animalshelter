package ru.devpro.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.core.entity.UserEntity;
import ru.devpro.animalshelter.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // создание пользователя
    @Operation(
            summary = "Запись пользователя в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Возвращает данные добавленного усыновителя",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserEntity.class)
                            )
                    )
            }
    )

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(userService.createUser(userEntity));
    }

    // нахождение пользователя по id
    @Operation(
            summary = "Поиск пользователя по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Возвращает данные по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserEntity.class)
                            )
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<UserEntity> findUserById(@Parameter(description = "введите id пользователя", example = "1")
                                                   @PathVariable Long id) {
        UserEntity userEntity = userService.findUserById(id);
        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userEntity);
    }

    // добавления животного пользователю по id
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает данные добавленного питомца и усыновителя"
            )
    })


    // удаление пользователя
    @Operation(
            summary = "Удаление пользователя из БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленные данные из Бд",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserEntity.class)
                            )
                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // нахождение всех пользователей
    @Operation(
            summary = "нахождение всех пользователей в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Вывод всех пользователей из Бд",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UserEntity.class))
                            )
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<Collection<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
