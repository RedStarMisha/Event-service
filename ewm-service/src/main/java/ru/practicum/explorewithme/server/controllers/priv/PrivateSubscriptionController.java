package ru.practicum.explorewithme.server.controllers.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.subscription.NewSubscriptionRequest;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.models.subscription.group.GroupDto;
import ru.practicum.explorewithme.models.subscription.group.NewGroupDto;
import ru.practicum.explorewithme.server.services.priv.PrivateSubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PrivateSubscriptionController {

    private final PrivateSubscriptionService service;

    @PostMapping("/subscriptions/{publisherId}/subscribe")
    public SubscriptionRequestDto addSubscribe(@RequestHeader("X-EWM-User-Id") long userId,
                                               @PathVariable(name = "publisherId") Long publisherId,
                                               @RequestBody NewSubscriptionRequest request) {
        return service.addSubscribe(userId, publisherId, request);
    }

    @GetMapping("/subscriptions/{subscriptionId}")
    public SubscriptionRequestDto getSubscriptionById(@RequestHeader("X-EWM-User-Id") Long userId,
                                                      @PathVariable(name = "subscriptionId") Long subscriptionId) {
        return service.getSubscriptionById(userId, subscriptionId);
    }

    @PatchMapping("/subscriptions/{subscriptionId}/cancel")
    public SubscriptionRequestDto cancelSubscription(@RequestHeader("X-EWM-User-Id") Long userId,
                                                     @PathVariable(name = "subscriptionId") Long subscriptionId) {
        return service.cancelSubscription(userId, subscriptionId);
    }

    @PatchMapping("/subscriptions/{subscriptionId}/accept")
    public SubscriptionRequestDto acceptSubscribe(@RequestHeader("X-EWM-User-Id") Long userId,
                                                  @PathVariable(name = "subscriptionId") Long subscriptionId,
                                                  @RequestParam(name = "friendship") Boolean friendship) {

        return service.acceptSubscribe(userId, subscriptionId, friendship);
    }

    @GetMapping("/subscriptions/incoming")
    public List<SubscriptionRequestDto> getIncomingSubscriptions(@RequestHeader("X-EWM-User-Id") Long userId,
                                                                 @RequestParam(name = "status", required = false) SubscriptionStatus status,
                                                                 @RequestParam(name = "from") int from, @RequestParam(name = "size") int size) {
        return service.getIncomingSubscriptions(userId, status, from, size);
    }

    @GetMapping("/subscriptions/outgoing")
    public List<SubscriptionRequestDto> getOutgoingSubscriptions(@RequestHeader("X-EWM-User-Id") Long userId,
                                                                 @RequestParam(name = "status", required = false) SubscriptionStatus status,
                                                                 @RequestParam(name = "from") int from, @RequestParam(name = "size") int size) {
        return service.getOutgoingSubscriptions(userId, status, from, size);
    }

    @PostMapping("/groups")
    public GroupDto addNewGroup(@RequestHeader("X-EWM-User-Id") Long userId,
                                @RequestBody NewGroupDto groupDto) {
        return service.addNewGroup(userId, groupDto);
    }

    @GetMapping("/groups")
    public List<GroupDto> getGroups(@RequestHeader("X-EWM-User-Id") Long userId) {
        return service.getGroups(userId);
    }
}
