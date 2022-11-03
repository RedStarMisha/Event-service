package ru.practicum.explorewithme.server.services.priv.strategy;

import org.springframework.data.domain.Pageable;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;

import java.util.List;

public interface Strategy {

    SubscriptionStatus getStatus();

    List<SubscriptionRequestDto> findSubscriptionByStrategy(Long userId, Pageable page);
}
