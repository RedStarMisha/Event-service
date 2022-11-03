package ru.practicum.explorewithme.server.services.priv.strategy.foritemowner.strategyimpl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.server.repositories.SubscriptionRepository;
import ru.practicum.explorewithme.server.services.priv.strategy.foritemowner.StrategyForFollower;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.booking.strategy.BookingState;
import ru.practicum.shareit.booking.strategy.foritemowner.StrategyForItemOwner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class StrategyForFollowerSubscribe implements StrategyForFollower {
    private final SubscriptionRepository repository;

    @Override
    public SubscriptionStatus getStatus() {
        return SubscriptionStatus.SUBSCRIPTION;
    }

    @Override
    public List<SubscriptionRequestDto> findSubscriptionByStrategy(Long followerId, Pageable page) {
        return bookingRepository.findCurrentBookingsByItemOwnerId(ownerId, date, page).stream()
                .map(BookingMapper::toBookingDto).collect(Collectors.toList());
    }
}
