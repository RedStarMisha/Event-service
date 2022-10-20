package ru.practicum.explorewithme.admin;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.clients.admin.UserClient;
import ru.practicum.explorewithme.models.user.NewUserRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class UserController {

    private final UserClient client;

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(@RequestParam(name = "ids", required = false) long[] ids,
                                           @RequestParam(name = "from") @PositiveOrZero Integer from,
                                           @RequestParam(name = "size") @Positive int size) {
        return client.getUsers(ids, from, size);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUsers(@RequestBody @Valid NewUserRequest newUserRequest) {
        return client.addUser(newUserRequest);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(name = "userId") long userId) {
        return client.deleteUser(userId);
    }
}
