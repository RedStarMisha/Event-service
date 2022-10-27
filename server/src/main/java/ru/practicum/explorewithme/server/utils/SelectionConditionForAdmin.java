package ru.practicum.explorewithme.server.utils;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.server.models.QEvent;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.ServerUtil.makePageable;

@Getter
@ToString
public class SelectionConditionForAdmin {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private int[] users;
    private int[] states;
    private int[] categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private int from;
    private int size;

    private SelectionConditionForAdmin(int[] users, int[] states, int[] categories, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, int from, int size) {
        this.users = users;
        this.states = states;
        this.categories = categories;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.from = from;
        this.size = size;
    }

    public static SelectionConditionForAdmin of(int[] users, int[] states, int[] categories, String start, String end,
                                                int from, int size) {
        LocalDateTime startDate = start != null ? LocalDateTime.parse(start, formatter) : null;
        LocalDateTime endDate = start != null ? LocalDateTime.parse(end, formatter) : null;
        return new SelectionConditionForAdmin(users, states, categories, startDate, endDate, from, size);
    }

    public SearchParam getSearchParameters(QEvent event) {
        List<BooleanExpression> parameters = new ArrayList<>();

        if (users != null) {
            List<Long> userIds = Arrays.stream(users).mapToLong(i -> i).boxed().collect(Collectors.toList());
            BooleanExpression usersExpression = event.initiator.id.in(userIds);
            parameters.add(usersExpression);
        }

        if (states != null) {
            List<State> stateList = Arrays.stream(states).mapToObj(ind -> State.values()[ind]).collect(Collectors.toList());
            BooleanExpression statesExpression = event.state.in(stateList);
            parameters.add(statesExpression);
        }

        if (categories != null) {
            List<Long> catIds = Arrays.stream(categories).mapToLong(i -> i).boxed().collect(Collectors.toList());
            BooleanExpression categoryExpression = event.category.id.in(catIds);
            parameters.add(categoryExpression);
        }

        parameters.add(dateExpression(rangeStart, rangeEnd, event));

        BooleanExpression searchExpression = parameters.stream().reduce(BooleanExpression::and).get();

        return new SearchParam(searchExpression, makePageable(from, size));
    }

    private BooleanExpression dateExpression(LocalDateTime rangeStart, LocalDateTime rangeEnd, QEvent event) {
        if (rangeStart == null && rangeEnd == null) {
            return event.eventDate.after(LocalDateTime.now());
        } else if (rangeStart == null && rangeEnd != null) {
            return event.eventDate.before(rangeEnd);
        } else if (rangeStart != null && rangeEnd == null) {
            return event.eventDate.after(rangeStart);
        } else {
            return event.eventDate.between(rangeStart, rangeEnd);
        }
    }

    @AllArgsConstructor
    @Getter
    public class SearchParam {
        private BooleanExpression booleanExpression;
        private Pageable pageable;
    }
}
