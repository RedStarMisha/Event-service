package ru.practicum.explorewithme.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.clients.server.priv.SubscriptionClient;
import ru.practicum.explorewithme.models.user.UserShortDto;
import ru.practicum.explorewithme.models.user.UserWithSubscriptionDto;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/users")
public class PrivateUserController {

    private final SubscriptionClient client;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@RequestHeader("X-EWM-User-Id") long followerId,
                                    @PathVariable(name = "userId") long userId) {
        return client.getUser(followerId, userId);
    }

//    @GetMapping("/{userId}/following")
//    public ResponseEntity<Object> getFollowing(@RequestHeader("X-EWM-User-Id") long followerId,
//                                           @PathVariable(name = "userId") long userId,
//                                           @RequestParam(name = "friends") boolean friends,
//                                           @RequestParam(name = "from", defaultValue = "0") int from,
//                                           @RequestParam(name = "size", defaultValue = "10") int size) {
//
//        return client.getFollowing(followerId, userId, friends, from, size);
//    }
//    @GetMapping("/{userId}/followers")
//    public ResponseEntity<Object> getFollowers(@RequestHeader("X-EWM-User-Id") long followerId,
//                                           @PathVariable(name = "userId") long userId,
//                                           @RequestParam(name = "friends") boolean friends,
//                                           @RequestParam(name = "from", defaultValue = "0") int from,
//                                           @RequestParam(name = "size", defaultValue = "10") int size) {
//
//        return client.getFollowers(followerId, userId, friends, from, size);
//    }
}
