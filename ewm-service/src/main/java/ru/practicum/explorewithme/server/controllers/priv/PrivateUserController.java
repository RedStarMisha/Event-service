package ru.practicum.explorewithme.server.controllers.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.user.UserShortDto;
import ru.practicum.explorewithme.models.user.UserWithSubscriptionDto;
import ru.practicum.explorewithme.server.services.priv.PrivateUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/users")
public class PrivateUserController {

    private final PrivateUserService userService;

    @GetMapping("/{userId}")
    public UserWithSubscriptionDto getUser(@RequestHeader("X-EWM-User-Id") long followerId,
                                           @PathVariable(name = "userId") long userId) {
        return userService.getUser(followerId, userId);
    }

    @GetMapping("/{userId}/following")
    public List<UserShortDto> getFollowing(@RequestHeader("X-EWM-User-Id") long followerId,
                                           @PathVariable(name = "userId") long userId,
                                           @RequestParam(name = "friends") boolean friends,
                                           @RequestParam(name = "from", defaultValue = "0") int from,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {

        return userService.getFollowing(followerId, userId, friends, from, size);
    }
    @GetMapping("/{userId}/followers")
    public List<UserShortDto> getFollowers(@RequestHeader("X-EWM-User-Id") long followerId,
                                           @PathVariable(name = "userId") long userId,
                                           @RequestParam(name = "friends") boolean friends,
                                           @RequestParam(name = "from", defaultValue = "0") int from,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {

        return userService.getFollowers(followerId, userId, friends, from, size);
    }
}
