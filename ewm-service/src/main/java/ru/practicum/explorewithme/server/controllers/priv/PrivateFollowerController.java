package ru.practicum.explorewithme.server.controllers.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.subscription.FollowerDto;
import ru.practicum.explorewithme.models.subscription.UpdateFollowerDto;
import ru.practicum.explorewithme.models.user.UserShortDto;
import ru.practicum.explorewithme.models.user.UserWithSubscriptionDto;
import ru.practicum.explorewithme.server.services.priv.PrivateFollowerService;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/users")
public class PrivateFollowerController {

    private final PrivateFollowerService followerService;

    @GetMapping("/{userId}")
    public UserWithSubscriptionDto getUser(@RequestHeader("X-EWM-User-Id") long followerId,
                                           @PathVariable(name = "userId") long userId) {
        return followerService.getUser(followerId, userId);
    }

    @GetMapping("/{userId}/following")
    public List<FollowerDto> getFollowing(@RequestHeader("X-EWM-User-Id") long followerId,
                                           @PathVariable(name = "userId") long userId,
                                           @RequestParam(name = "friends") boolean friends,
                                           @RequestParam(name = "from", defaultValue = "0") int from,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {

        return followerService.getFollowing(followerId, userId, friends, from, size);
    }



    @GetMapping("/{userId}/followers")
    public List<FollowerDto> getFollowers(@RequestHeader("X-EWM-User-Id") long followerId,
                                           @PathVariable(name = "userId") long userId,
                                           @RequestParam(name = "friends") boolean friends,
                                           @RequestParam(name = "from", defaultValue = "0") int from,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {

        return followerService.getFollowers(followerId, userId, friends, from, size);
    }
    @GetMapping("/following")
    public List<FollowerDto> getOwnFollowing(@RequestHeader("X-EWM-User-Id") long userId,
                                                 @RequestParam(name = "friends", defaultValue = "true") boolean friends,
                                                 @RequestParam(name = "from", defaultValue = "0") int from,
                                                 @RequestParam(name = "size", defaultValue = "10") int size) {

        return followerService.getOwnFollowing(userId, friends, from, size);
    }

    @GetMapping("/followers")
    public List<FollowerDto> getOwnFollowers(@RequestHeader("X-EWM-User-Id") long userId,
                                                  @RequestParam(name = "friends", defaultValue = "true") boolean friends,
                                                  @RequestParam(name = "group", required = false) Long groupId,
                                                  @RequestParam(name = "from", defaultValue = "0") int from,
                                                  @RequestParam(name = "size", defaultValue = "10") int size) {

        return followerService.getOwnFollowers(userId, friends, groupId, from, size);
    }
    @PatchMapping("/followers/{followerId}")
    public FollowerDto updateFollower(@RequestHeader("X-EWM-User-Id") long publisherId,
                                      @PathVariable(name = "followerId") long followerId,
                                      @RequestBody UpdateFollowerDto follower) {

        return followerService.updateFollower(publisherId, followerId, follower);
    }
}
