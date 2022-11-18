package ru.practicum.explorewithme.server.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.user.NewUserRequest;
import ru.practicum.explorewithme.models.user.UserDto;
import ru.practicum.explorewithme.server.services.admin.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam(name = "ids") long[] ids,
                                  @RequestParam(name = "from") int from,
                                  @RequestParam(name = "size") int size) {
        return service.getUsers(ids, from, size);
    }

    @PostMapping("/users")
    public UserDto addUsers(@RequestBody NewUserRequest newUserRequest) {
        return service.addUser(newUserRequest);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable(name = "userId") long userId) {
        service.deleteUser(userId);
    }
}
