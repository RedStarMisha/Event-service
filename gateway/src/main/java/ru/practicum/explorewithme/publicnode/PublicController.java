package ru.practicum.explorewithme.publicnode;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.clients.PublicClient;
import ru.practicum.explorewithme.exceptions.IncorrectDateException;
import ru.practicum.explorewithme.exceptions.UnknownEnumElementException;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PublicController {

    private final PublicClient client;

    @GetMapping("/events")
    public ResponseEntity<Object> getEvents(@RequestParam(name = "text", required = false) String text,
                                           @RequestParam(name = "categories", required = false) int[] categories,
                                           @RequestParam(name = "paid", required = false) Boolean paid,
                                           @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                           @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                           @RequestParam(name = "available", defaultValue = "false") boolean available,
                                           @RequestParam(name = "sort", defaultValue = "EVENT_DATE") String sort,
                                           @RequestParam(name = "from", defaultValue = "0") int from,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {

        EventSort sortType = EventSort.from(sort).orElseThrow(()-> new UnknownEnumElementException("Неизвестный тип сортировки"));

        dateValidation(rangeStart, rangeEnd);

        Map<String, Object> parameters = Map.of(
                "text", text,
                "categories", categories,
                "paid", paid,
                "rangeStart", rangeStart,
                "rangeEnd", rangeEnd,
                "available", available,
                "sort", sort,
                "from", from,
                "size", size
        );
        return client.getEvents(parameters);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<Object> getEventById(@PathVariable(name = "eventId") int eventId) {
        return client.getEventById(eventId);
    }

    @GetMapping("/compilation")
    public ResponseEntity<Object> getCompilations(@RequestParam(name = "pinned", required = false) boolean pinned,
                                                  @RequestParam(name = "from", defaultValue = "0") int from,
                                                  @RequestParam(name = "size", defaultValue = "10") int size) {
        Map<String, Object> param = Map.of(
                "pinned", pinned,
                "from", from,
                "size", size);
        return client.getCompilations(param);
    }

    @GetMapping("/compilation/{compilationId}")
    public ResponseEntity<Object> getCompilationById(@PathVariable(name = "compilationId") Long compilationId) {
        return client.getCompilationById(compilationId);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Object> getCategoryById(@PathVariable(name = "categoryId") Long categoryId) {
        return client.getCategoryById(categoryId);
    }

    @GetMapping("/categories")
    public ResponseEntity<Object> getCategories(@RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        return client.getCategories(from, size);
    }

    private static void dateValidation(String start, String end) {
        LocalDateTime rangeStart = LocalDateTime.parse(start);
        LocalDateTime rangeEnd = LocalDateTime.parse(end);
        if (rangeStart.isBefore(rangeEnd)) {
            throw new IncorrectDateException("Введенные даны некорректны");
        }
    }
}
