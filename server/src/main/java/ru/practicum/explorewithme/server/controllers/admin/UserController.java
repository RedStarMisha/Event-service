package ru.practicum.explorewithme.server.controllers.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.user.NewUserRequest;
import ru.practicum.explorewithme.models.user.UserDto;
import ru.practicum.explorewithme.server.services.admin.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class UserController {

    private final UserService service;

    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam(name = "ids") long[] ids,
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
