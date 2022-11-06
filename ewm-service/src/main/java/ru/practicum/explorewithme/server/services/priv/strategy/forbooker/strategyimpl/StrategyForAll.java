package ru.practicum.explorewithme.server.services.priv.strategy.forbooker.strategyimpl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.models.event.EventState;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.models.User;
import ru.practicum.explorewithme.server.repositories.EventRepository;
import ru.practicum.explorewithme.server.services.priv.strategy.forbooker.StrategyForBooker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class StrategyForAll implements StrategyForBooker {
    private final EventRepository eventRepository;

    @Override
    public EventState getState() {
        return EventState.ALL;
    }

    @Override
    public List<Event> findEventsByStrategy(User user, LocalDateTime start, LocalDateTime end, Pageable page) {

        return null;
    }

    @Override
    public List<BookingDto> findBookingByStrategy(Long bookerId, Pageable page) {
        return bookingRepository.findAllByBooker_Id(bookerId, page).stream()
                .map(BookingMapper::toBookingDto).collect(Collectors.toList());
    }
}
