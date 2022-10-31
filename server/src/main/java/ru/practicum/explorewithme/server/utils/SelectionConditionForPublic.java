package ru.practicum.explorewithme.server.utils;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.explorewithme.models.event.EventSort;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.models.QEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.server.utils.ServerUtil.makePageable;

@Getter
public class SelectionConditionForPublic {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String text;
    private int[] categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean available;
    private EventSort sort;
    private int from;
    private int size;

    private SelectionConditionForPublic(String text, int[] categories, Boolean paid, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, Boolean available, EventSort sort, int from, int size) {
        this.text = text;
        this.categories = categories;
        this.paid = paid;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.available = available;
        this.sort = sort;
        this.from = from;
        this.size = size;
    }

    public static SelectionConditionForPublic of (String text, int[] categories, Boolean paid, String rangeStart,
                                              String rangeEnd, Boolean available, EventSort sort, int from, int size) {

        LocalDateTime start = rangeStart != null && !rangeStart.equals("") ? LocalDateTime.parse(rangeStart, formatter) : null;
        LocalDateTime end = rangeEnd != null && !rangeEnd.equals("") ? LocalDateTime.parse(rangeEnd, formatter) : null;

        return new SelectionConditionForPublic(text, categories, paid, start, end, available, sort, from, size);
    }

    public SearchParam getSearchParameters(QEvent event) {
        List<BooleanExpression> parameters = new ArrayList<>();

        if (text != null && !text.equals("")) {
            BooleanExpression statesExpression = event.annotation.likeIgnoreCase(text)
                    .or(event.description.likeIgnoreCase(text));
            parameters.add(statesExpression);
        }

        if (categories != null && categories.length != 0) {
            List<Long> catIds = Arrays.stream(categories).mapToLong(i -> i).boxed().collect(Collectors.toList());
            BooleanExpression categoryExpression = event.category.id.in(catIds);
            parameters.add(categoryExpression);
        }

        if (paid != null) {
            parameters.add(event.paid.eq(paid));
        }

        parameters.add(event.state.eq(State.PUBLISHED));

        parameters.add(dateExpression(rangeStart, rangeEnd, event));

        if (available != null && available) {
            parameters.add(event.numberConfirmed.lt(event.participantLimit));
        }

        Pageable pageable = makePageable(from, size);

        if (sort != null && sort == EventSort.EVENT_DATE) {
            Sort sorting = Sort.by("eventDate").descending();
            pageable = makePageable(from, size, sorting);
        }

        BooleanExpression searchExpression = parameters.stream().reduce(BooleanExpression::and).get();

        return new SearchParam(searchExpression, pageable);
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
}
