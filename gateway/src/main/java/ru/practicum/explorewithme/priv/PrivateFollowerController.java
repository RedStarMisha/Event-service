package ru.practicum.explorewithme.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.clients.server.priv.SubscriptionClient;
import ru.practicum.explorewithme.models.subscription.UpdateFollowerDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/users")
public class PrivateFollowerController {

    private final SubscriptionClient client;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@RequestHeader("X-EWM-User-Id") long userFollowerId,
                                          @PathVariable(name = "userId") long userId) {
        return client.getUser(userFollowerId, userId);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<Object> getFollowing(@RequestHeader("X-EWM-User-Id") long userFollowerId,
                                               @PathVariable(name = "userId") long userId,
                                               @RequestParam(name = "friends", defaultValue = "false") boolean friends,
                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                               @RequestParam(name = "size", defaultValue = "10") int size) {

        return client.getFollowing(userFollowerId, userId, friends, from, size);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<Object> getFollowers(@RequestHeader("X-EWM-User-Id") long userFollowerId,
                                               @PathVariable(name = "userId") long userId,
                                               @RequestParam(name = "friends", defaultValue = "false") boolean friends,
                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                               @RequestParam(name = "size", defaultValue = "10") int size) {

        return client.getFollowers(userFollowerId, userId, friends, from, size);
    }

    @GetMapping("/following")
    public ResponseEntity<Object> getOwnFollowing(@RequestHeader("X-EWM-User-Id") long userId,
                                                  @RequestParam(name = "friends", defaultValue = "true") boolean friends,
                                                  @RequestParam(name = "from", defaultValue = "0") int from,
                                                  @RequestParam(name = "size", defaultValue = "10") int size) {

        return client.getOwnFollowing(userId, friends, from, size);
    }

    @GetMapping("/followers")
    public ResponseEntity<Object> getOwnFollowers(@RequestHeader("X-EWM-User-Id") long userId,
                                                  @RequestParam(name = "friends", defaultValue = "true") boolean friends,
                                                  @RequestParam(name = "group", required = false) Long groupId,
                                                  @RequestParam(name = "from", defaultValue = "0") int from,
                                                  @RequestParam(name = "size", defaultValue = "10") int size) {

        return client.getOwnFollowers(userId, friends, groupId, from, size);
    }

    @PatchMapping("/followers/{followerId}")
    public ResponseEntity<Object> updateFollower(@RequestHeader("X-EWM-User-Id") long publisherId,
                                                 @PathVariable(name = "followerId") long followerId,
                                                 @RequestBody @Valid UpdateFollowerDto follower) {

        return client.updateFollower(publisherId, followerId, follower);
    }

}
