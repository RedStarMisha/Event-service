package ru.practicum.explorewithme.server.utils.selectioncondition;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.lang.Nullable;
import ru.practicum.explorewithme.models.event.EventState;
import ru.practicum.explorewithme.server.models.QEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.explorewithme.server.utils.ServerUtil.convertToDate;
import static ru.practicum.explorewithme.server.utils.ServerUtil.makePageable;

public class SelectionConditionForPrivate {

    private final long userId;
    private final EventState state;
    private final LocalDateTime rangeStart;
    private final LocalDateTime rangeEnd;
    private final Boolean available;
    private final int from;
    private final int size;

    private SelectionConditionForPrivate(long userId, EventState state, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Boolean available, int from, int size) {
        this.userId = userId;
        this.state = state;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.available = available;
        this.from = from;
        this.size = size;
    }

    public static SelectionConditionForPrivate of(long userId, EventState state, @Nullable String start,
                                                  @Nullable String end, @Nullable Boolean available, int from, int size) {
        LocalDateTime startDate = convertToDate(start);
        LocalDateTime endDate = convertToDate(end);
        return new SelectionConditionForPrivate(userId, state, startDate, endDate, available, from, size);
    }

    public SearchParam getSearchParametersParticipant(QEvent event, List<Long> ids) {
        List<BooleanExpression> parameters = getBase(event);

        parameters.add(event.id.in(ids));

        BooleanExpression searchExpression = parameters.stream().reduce(BooleanExpression::and).get();

        return new SearchParam(searchExpression, makePageable(from, size));
    }

    public SearchParam getSearchParametersCreator(QEvent event) {
        List<BooleanExpression> parameters = getBase(event);

        parameters.add(event.initiator.id.eq(userId));

        BooleanExpression searchExpression = parameters.stream().reduce(BooleanExpression::and).get();

        return new SearchParam(searchExpression, makePageable(from, size));
    }

    private List<BooleanExpression> getBase(QEvent event) {
        List<BooleanExpression> parameters = new ArrayList<>();

        switch (state) {
            case PAST:
                parameters.add(event.eventDate.before(LocalDateTime.now()));
            case FUTURE:
                parameters.add(event.eventDate.after(LocalDateTime.now()));
        }
        if (available != null) {
            parameters.add(event.numberConfirmed.lt(event.participantLimit));
        }

        if (rangeStart != null || rangeEnd != null) {
            parameters.add(dateExpression(rangeStart, rangeEnd, event));
        }
        return parameters;
    }

    private BooleanExpression dateExpression(LocalDateTime rangeStart, LocalDateTime rangeEnd, QEvent event) {
        if (rangeStart == null && rangeEnd != null) {
            return event.eventDate.before(rangeEnd);
        } else if (rangeStart != null && rangeEnd == null) {
            return event.eventDate.after(rangeStart);
        } else if (rangeStart != null) {
            return event.eventDate.between(rangeStart, rangeEnd);
        }
        return null;
    }
}
