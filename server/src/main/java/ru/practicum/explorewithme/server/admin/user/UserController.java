package ru.practicum.explorewithme.server.admin.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.user.NewUserRequest;
import ru.practicum.explorewithme.models.user.UserDto;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class UserController {

    private final UserService service;

    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) long[] ids,
                                  @RequestParam(name = "from") int from,
                                  @RequestParam(name = "size")  int size) {
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
